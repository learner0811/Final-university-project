package sa.thread.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sa.thread.entity.Image;
import sa.thread.mapper.ImageMapper;

@Repository
public class ImageDao implements BaseDao<Image> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Image save(final Image image) {		
		KeyHolder keyHolder = new GeneratedKeyHolder();		
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "insert into image (file_name, status, type, iddata) values (?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, image.getFileName());
				ps.setInt(2, 1);
				ps.setInt(3, image.getType());
				ps.setInt(4, image.getIdData());
				return ps;
			}
		}, keyHolder);		
		image.setIdImage(keyHolder.getKey().intValue());		
		return image;
	}

	@Override
	public Image update(Image image) {
		Object[] params = {image.getFileName(), image.getStatus(), image.getType(), image.getIdData(), image.getIdImage()};
		int[] types = {Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER};
		String updateSql = "update image set file_name = ?, status = ?, type = ?, iddata = ? where idimage = ?";
		int rows = jdbcTemplate.update(updateSql, params, types);		 		   		
		System.out.println(rows + " row(s) updated.");
		return image;
	}

	@Override
	public Image findById(int primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Image> findByParam(Map<String, Object> params) {
		String sql = "select * from image where 1=1 ";		
		List<Object> args = new ArrayList<Object>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			sql +=  " and " + entry.getKey() + " = ?"; 
			args.add(entry.getValue());
		}		
		return jdbcTemplate.query(sql, args.toArray(), new ImageMapper());
	}

	@Override
	public Iterable<Image> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Image entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existsById(int primaryKey) {
		// TODO Auto-generated method stub
		return false;
	}

}

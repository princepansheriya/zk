package util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisUtil {
	private static final SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String resource = "mybatis-config.xml";
			InputStream inputStream = MyBatisUtil.class.getClassLoader().getResourceAsStream(resource);
			if (inputStream == null) {
				throw new RuntimeException("MyBatis configuration file not found: " + resource);
			}
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error initializing MyBatis SqlSessionFactory: " + e.getMessage());
		}
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}

package test;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;

import com.jt.sys.dao.SysConfigDao;
import com.jt.sys.entity.SysConfig;

public class Testsql extends TestBase{
	
	@Test
	public void testsqlSessionFactory()  {
		SqlSessionFactory ds=ctx.getBean(SqlSessionFactory.class);
		SqlSessionFactoryBean session = ctx.getBean("&sqlSessionFactory",SqlSessionFactoryBean.class);
		System.out.println(ds+":"+session);
		
	}
	@Test
	public void testSysConfigDao() {
		SqlSessionFactory ds=ctx.getBean("sqlSessionFactory",SqlSessionFactory.class);
		System.out.println("2");
		SqlSession session = ds.openSession();
		System.out.println("3");
		SysConfigDao dao=session.getMapper(SysConfigDao.class);
		System.out.println("4");
		SysConfig cf =dao.findById(4);
		System.out.println(cf.getName());
		session.close();
	}
	 @Test
	 public void testSysConfigDao01(){
		 SqlSessionFactory ssf=
		 ctx.getBean("sqlSessionFactory",SqlSessionFactory.class);
		 SqlSession session=ssf.openSession();
		 SysConfigDao dao= session.getMapper(SysConfigDao.class);
		 SysConfig config=dao.findById(4);//mybatis自动完成映射操作(反射)
		 System.out.println(config.getName());
		 Integer[] arry = new Integer[Integer.MAX_VALUE-2];
		 System.out.println(Integer.MAX_VALUE+";rry:"+arry.toString());
		 session.close();
	 }
	 @Test
	 public void testSysConfigDao02(){
		 SysConfigDao dao = ctx.getBean(SysConfigDao.class);
		 SysConfig config = dao.findById(4);
		 System.out.println(config.getName());
		 
		 
	 }
	
}	

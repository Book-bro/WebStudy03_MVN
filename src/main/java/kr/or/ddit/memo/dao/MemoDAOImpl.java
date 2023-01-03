package kr.or.ddit.memo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.vo.MemoVO;

public class MemoDAOImpl implements MemoDAO {
	
	private SqlSessionFactory SqlSessionFactory = MybatisUtils.getSqlSessionFactory();  //무조건 지역변수로만 사용
	
	@Override
	public List<MemoVO> selectMemoList() {
		try(
			SqlSession sqlSession =  SqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class); //계속 생성해주므로 전역으로 만들 필요성이 있음, 그것이 mybatis scanner
			return mapperProxy.selectMemoList();
//			return sqlSession.selectList("kr.or.ddit.memo.dao.MemoDAO.selectMemoList");
		}
	}

	@Override
	public int insertMemo(MemoVO memo) {
		try(
			SqlSession sqlSession =  SqlSessionFactory.openSession(); //트랜잭션(ACID) 시작
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class); //config.xml에서 설정한 mapper
			int rowcnt = mapperProxy.insertMemo(memo); //프록시 사용 방법, 밑의 문제를 해결해줌
//			int rowcnt = sqlSession.insert("kr.or.ddit.memo.dao.MemoDAO.insertMemo", memo); //id만 넘겨도 오류가 안나서 실수를 많이하기에 다른방법도 있음
			sqlSession.commit(); //insert, update, delete는 commit해줘야함, 트랜잭션 종료
			return rowcnt;
		}
	}

	@Override
	public int updateMemo(MemoVO memo) {
		try(
			SqlSession sqlSession =  SqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.updateMemo(memo);
//			int rowcnt = sqlSession.update("kr.or.ddit.memo.dao.MemoDAO.updateMemo", memo);
			sqlSession.commit();
			return rowcnt;
		}
	}

	@Override
	public int deleteMemo(int code) {
		try(
			SqlSession sqlSession =  SqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.deleteMemo(code);
//			int rowcnt = sqlSession.delete("kr.or.ddit.memo.dao.MemoDAO.deleteMemo", code);
			sqlSession.commit();
			return rowcnt;
		}
	}

}

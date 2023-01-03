package kr.or.ddit.memo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.MemoVO;

public interface MemoDAO {
   public List<MemoVO> selectMemoList();// 모든메모 가져오기
   public int insertMemo(MemoVO memo); // count
   public int updateMemo(MemoVO memo);
   public int deleteMemo(@Param("code") int code); //이름 없는 값에 이름설정
   
}
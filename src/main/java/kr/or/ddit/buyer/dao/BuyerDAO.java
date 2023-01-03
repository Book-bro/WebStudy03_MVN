package kr.or.ddit.buyer.dao;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public interface BuyerDAO {
	public int selectTotalRecord(PagingVO<BuyerVO> pagingVO);
	public List<BuyerVO> selectBuyerList(PagingVO<BuyerVO> pagingVO);
	public BuyerVO selectBuyer(String buyerId);
	public int insertBuyer(BuyerVO buyer);
	public int updateBuyer(BuyerVO buyer);
}

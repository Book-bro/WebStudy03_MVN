package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public interface BuyerService {
	public List<BuyerVO> retrieveBuyerList(PagingVO<BuyerVO> pagingVO);
	public BuyerVO retrieveBuyer(String buyerId);
	public int createBuyer(BuyerVO buyer);
	public int modifyBuyer(BuyerVO buyer);
}

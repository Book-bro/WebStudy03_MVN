package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.buyer.dao.BuyerDAO;
import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public class BuyerServiceImpl implements BuyerService {
	
	private BuyerDAO buyerDAO = new BuyerDAOImpl();

	@Override
	public List<BuyerVO> retrieveBuyerList(PagingVO<BuyerVO> pagingVO) {
		pagingVO.setTotalRecord(buyerDAO.selectTotalRecord(pagingVO));
		pagingVO.setDataList(buyerDAO.selectBuyerList(pagingVO));
		return pagingVO.getDataList();
	}

	@Override
	public BuyerVO retrieveBuyer(String buyerId) {
		BuyerVO buyer = buyerDAO.selectBuyer(buyerId);
		if(buyer==null)
			throw new RuntimeException(String.format("%s 상품 없음.", buyerId));
		return buyer;
	}

	@Override
	public int createBuyer(BuyerVO buyer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int modifyBuyer(BuyerVO buyer) {
		// TODO Auto-generated method stub
		return 0;
	}

}

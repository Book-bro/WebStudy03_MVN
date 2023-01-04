package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements ProdService{
	//의존관계 형성
	private ProdDAO prodDAO = new ProdDAOImpl();
	
	
	@Override
	public ProdVO retrieveProd(String prodId) {
		ProdVO prod = prodDAO.selectProd(prodId);
		if(prod==null)
			throw new RuntimeException(String.format("%s는 없는 상품", prodId));
		return prod;
	}


	@Override
	public void retrieveProdList(PagingVO<ProdVO> pagingVO) {
		int totalRecord = prodDAO.selectTotalRecord(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<ProdVO> dataList = prodDAO.selectProdList(pagingVO);
		pagingVO.setDataList(dataList);
	}


	@Override
	public ServiceResult createProd(ProdVO prod) {
		int rowcnt = prodDAO.insertProd(prod);
		return rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}


	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		ServiceResult result = null;
		String prodId = prod.getProdId();
		if(prodId==null) {
			throw new RuntimeException(String.format("%s는 없는 상품", prodId));
		}
		int rowcnt = prodDAO.updateProd(prod);
		result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
		return result;
	}

}

package kr.or.ddit.prod.service;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public interface ProdService {
	/**
	 * @param prodId
	 * @return 존재하지 않는 경우, RuntimeException 발생.
	 */
	public ProdVO retrieveProd(String prodId);
	
	/**
	 * call by reference 구조에 따라 totalRecord와 dataList 를 pagingVO에 넣어줌.
	 * @param pagingVO
	 */
	public void retrieveProdList(PagingVO<ProdVO> pagingVO);
}

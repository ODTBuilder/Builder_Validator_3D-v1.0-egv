package com.gitrnd.qaproducer.qa.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gitrnd.qaproducer.qa.domain.QAProgress;
import com.gitrnd.qaproducer.qa.repository.QAProgressRepository;
import com.gitrnd.qaproducer.qa.service.QAProgressService;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * tb_progress Service 클래스.
 * 
 * @author DY.Oh
 *
 */
@Service("qaProgressService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class QAProgressServiceImpl extends EgovAbstractServiceImpl implements QAProgressService {

	@Resource(name = "qaProgressRepository")
	private QAProgressRepository progressRep;

	/**
	 * tb_progress DB 테이에서 pIdx에 해당하는 {@link QAProgress} 조회.
	 * 
	 * @param pIdx
	 *            tb_progress index
	 * @return pIdx에 해당하는 {@link QAProgress}
	 * 
	 * @author DY.Oh
	 */
	@Transactional(readOnly = true)
	public QAProgress retrieveQAProgressById(int pIdx) {
		return progressRep.retrieveQAProgressById(pIdx);
	}

	/**
	 * tb_progress DB 테이블에 pIdx에 {@link QAProgress} 삽입.
	 * 
	 * @param progress
	 *            {@link QAProgress}
	 * @return pIdx
	 * 
	 * @author DY.Oh
	 */
	public Integer insertQARequest(QAProgress progress) {
		return progressRep.insertQARequest(progress);
	}

	/**
	 * tb_progress DB 테이블 수정.
	 * 
	 * @param progress
	 *            {@link QAProgress}
	 * 
	 * @author DY.Oh
	 */
	public void updateQAState(QAProgress progress) {
		progressRep.updateQAState(progress);
	}

	/**
	 * tb_progress DB 테이블 수정.
	 * 
	 * @param progress
	 *            {@link QAProgress}
	 * 
	 * @author DY.Oh
	 */
	public void updateQAResponse(QAProgress progress) {
		progressRep.updateQAResponse(progress);
	}

	/**
	 * tb_progress DB 테이블 조회.
	 * 
	 * @return List<HashMap<String, Object>>
	 * 
	 * @author DY.Oh
	 */
	public List<HashMap<String, Object>> selectQAProgressList() {
		return progressRep.selectQAProgressList();
	}

	/**
	 * tb_progress DB 테이블 조회.
	 * 
	 * @param progress
	 * @return {@link QAProgress}
	 * 
	 * @author DY.Oh
	 */
	public QAProgress selectQAStartTime(QAProgress progress) {
		return progressRep.selectQAStartTime();
	}
}

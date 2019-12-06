package com.gitrnd.qaproducer.qa.service;

import java.util.HashMap;
import java.util.List;

import com.gitrnd.qaproducer.qa.domain.QAProgress;

public interface QAProgressService {
	public QAProgress retrieveQAProgressById(int pIdx);

	public Integer insertQARequest(QAProgress progress);

	/**
	 * tb_progress DB 테이블 수정.
	 * 
	 * @param progress
	 *            {@link QAProgress}
	 * 
	 * @author DY.Oh
	 */
	public void updateQAState(QAProgress progress);

	/**
	 * tb_progress DB 테이블 수정.
	 * 
	 * @param progress
	 *            {@link QAProgress}
	 * 
	 * @author DY.Oh
	 */
	public void updateQAResponse(QAProgress progress);

	/**
	 * tb_progress DB 테이블 조회.
	 * 
	 * @return List<HashMap<String, Object>>
	 * 
	 * @author DY.Oh
	 */
	public List<HashMap<String, Object>> selectQAProgressList();

	/**
	 * tb_progress DB 테이블 조회.
	 * 
	 * @param progress
	 * @return {@link QAProgress}
	 * 
	 * @author DY.Oh
	 */
	public QAProgress selectQAStartTime(QAProgress progress);
}

package com.company.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.dto.DomainRuleVO;
import com.company.dto.GeneralSurveyVO;
import com.company.dto.MedicineVO;
import com.company.dto.PatientSymptomVO;
import com.company.dto.PatientVO;
import com.company.dto.SymptomVO;
import com.company.mapper.RuleMapper;
import com.company.service.RuleService;

@Service
public class RuleServiceImpl implements RuleService{
	
	@Autowired
	private RuleMapper mapper;

	@Override
	public PatientVO getPatientSymptoms(String pNumber) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getPatientSymptoms(pNumber);
	}

	@Override
	public List<SymptomVO> getSearchSymptom(SymptomVO symptom) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getSearchSymptom(symptom);
	}

	@Override
	public void deleteSymptom(PatientSymptomVO symptomVO) throws Exception {
		// TODO Auto-generated method stub
		mapper.deleteSymptom(symptomVO);
	}

	@Override
	public void addSymptom(PatientSymptomVO symptom) throws Exception {
		// TODO Auto-generated method stub
		mapper.addSymptom(symptom);
	}

	@Override
	public List<DomainRuleVO> selectDomain() throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectDomain();
	}

	@Override
	public GeneralSurveyVO getTypeRule(int typenumber) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getTypeRule(typenumber);
	}
	@Override
	public MedicineVO getImageDirectory(String medicine) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getImageDirectory(medicine);
	}

}

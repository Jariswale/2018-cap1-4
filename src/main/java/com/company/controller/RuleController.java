package com.company.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.drools.DroolsSpringTest;
import com.company.dto.DomainRuleVO;
import com.company.dto.MedicineVO;
import com.company.dto.MemberVO;
import com.company.dto.PatientDiagnosisVO;
import com.company.dto.PatientSymptomVO;
import com.company.dto.PatientVO;
import com.company.dto.SymptomVO;
import com.company.service.MemberService;
import com.company.service.RuleService;

@Controller
public class RuleController {
	
	private static final Logger logger = LoggerFactory.getLogger(RuleController.class);
	
	@Inject
	private RuleService ruleService;
	@Inject
	private MemberService service;
	@Inject
	private DroolsSpringTest drools;
	
	private PatientVO patient;
	private PatientSymptomVO symptomVO;

	@PostConstruct // 생성자 annotation
	public void initialize()
	{ 
		patient = new PatientVO(); // 추후 빈객체로 사용할 예정
		symptomVO = new PatientSymptomVO();
		//patient = (PatientVO) BeanUtils.getBean("patientVO");
		
		List<DomainRuleVO> ruleList = null; // rules
		
		try {
			ruleList = ruleService.selectDomain();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object>ruleMap = new HashMap<String,Object>();
		
		for(DomainRuleVO obj : ruleList) // hashmap 
		{
			ruleMap.put(obj.getRuleID(), obj);
			obj.countAndSymptom(); //
		}
		
		drools = new DroolsSpringTest();
		drools.setRuleMap(ruleMap); // drools hash map setting
		
		
		logger.info("setRuleDomain complete!");
	}

	/**
	 * 환자 번호를 통해서 설문의 결과인 증상들을 검색 또한 환자 세부사항도 같이 refresh
	 * @throws Exception 
	 */
	 @RequestMapping(value ="/getPatientSymptoms", method = RequestMethod.GET)
	 public @ResponseBody PatientVO getPatientSymptoms(@RequestParam("pNumber") String pNumber) throws Exception
	 {
		
		try {
			System.out.println("///////////////////"+pNumber);
			patient = ruleService.getPatientSymptoms(pNumber); // 환자 번호로 증상 검색
			service.delWaitingList(pNumber);
			
			logger.info("환자 검색확인: "+ patient.getpName()+" "+patient.getAge()+" "+patient.symptomArr.size());
			for(int i=0; i< patient.symptomArr.size(); i++) 
			{	
				System.out.println(patient.symptomArr.get(i).getSymptom());
			}
		} catch(NullPointerException e) {
		}
		return patient;
	 }
	 
	 /**
	  * DB에서 증상이름으로 증상명 검색 
	  * @throws Exception 
	  */
	@RequestMapping(value ="/searchSymptom", method = RequestMethod.GET)
	public @ResponseBody List<SymptomVO> searchSymptom(SymptomVO symptom) throws Exception
	{
		List<SymptomVO> list = ruleService.getSearchSymptom(symptom);
		return list;
	}
	
	/**
	 *  선택된 증상 삭제
	 * @throws Exception
	 */
	 @RequestMapping(value ="/deleteSelectedSymptom", method = RequestMethod.POST)
	 public @ResponseBody PatientVO deleteSelectedSymptom(@RequestParam("symptom") String symptom) throws Exception
	 {
		System.out.println(symptom);
		symptomVO.setSymptom(symptom);
		symptomVO.setpNum(patient.getpNumber());
		symptomVO.setVisitDate(patient.diagnosis.getVisitDate());
		ruleService.deleteSymptom(symptomVO);
		for(PatientSymptomVO vo : patient.symptomArr) {
			if( vo.getSymptom().equals(symptom)) {
				patient.symptomArr.remove(vo);
				break;
			}
		}
		return patient;
	 }
	 
	 /**
	 * 검색한 증상 추가
	 * @throws Exception
	 */
	 @RequestMapping(value ="/addPatientSymptom", method = RequestMethod.POST)
	 public @ResponseBody PatientSymptomVO addPatientSymptom(@RequestParam("symptom") String symptom) throws Exception
	 {
		System.out.println(symptom);
		PatientSymptomVO vo = new PatientSymptomVO();
		vo.setSymptom(symptom); // 증상 추가
		vo.setVisitDate(patient.diagnosis.getVisitDate()); // 환자 방문날짜 기록
		vo.setpNum(patient.getpNumber()); // 환자 번호 기록
		vo.setDegree(6); // 증상 강도 default 6
		patient.symptomArr.add(vo); // DTO 객체 추가
		ruleService.addSymptom(vo);
		return vo;
	 }
	 /**
	  * 
	  * 검사하기 버튼을 클릭했을 경우
	  */
	 @RequestMapping(value ="/checkSymptom", method = RequestMethod.GET)
	 public @ResponseBody PatientDiagnosisVO viewDiagnosis(Locale locale) 
	 {
		logger.info("checkSymptom");
		
		patient.diagnosis.complexPrescr.clear();
		patient.diagnosis.simplePrescr.clear();
		patient.diagnosis.diagnosisArr.clear();
		drools.setPatient(patient); // 검사할 환자 object 셋팅
	
		
		patient = drools.checkSymptom(); // drools 엔진에게 환자 object send
		
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("단순증상 : "+patient.diagnosis.simplePrescr);
		System.out.println("복합증상 : "+patient.diagnosis.complexPrescr);
		
	    return patient.diagnosis;
	 }
	/**
	 * save after rule evaluation
	 */
	@RequestMapping(value="saveEvalRule")
	public String write(MemberVO memberVO) throws Exception
	{
		logger.info("ruleEvaluate");
		memberVO.setPatientNum(patient.getpNumber());
		memberVO.setPatientName(patient.getpName());
		memberVO.setDate(patient.getVisitDate());
		service.insertEvalRule(memberVO);
		return "redirect:/defaultPage"; //
	}
	
	/**
	 * HospitalResultPage 의사페이지의 결과 페이지 view
	 */
	@RequestMapping(value="/hospitalResultPage")
	public String hospitalResultPage(Model model) throws Exception
	{
		logger.info("hospitalResultPage");
		patient.diagnosis.setpName(patient.getpName());
		patient.diagnosis.setAge(patient.getAge());
		patient.diagnosis.setpNumber(patient.getpNumber());
		patient.diagnosis.setSex(patient.getSex());
		patient.diagnosis.setVisitDate(patient.getVisitDate());
		int num =0;
		for(String str : patient.diagnosis.getSimplePrescr())
		{
			MedicineVO result = null;
			try {
				result = ruleService.getImageDirectory(str);
				if(result.handspot != null) {
					if( num == 0 ) patient.diagnosis.setHandspot1(result.handspot);
					else if( num == 1 ) patient.diagnosis.setHandspot2(result.handspot);
					else if( num == 2 ) patient.diagnosis.setHandspot3(result.handspot);
					else if( num == 3 ) patient.diagnosis.setHandspot4(result.handspot);
					else if( num == 4 ) patient.diagnosis.setHandspot5(result.handspot);
					else if( num == 5 ) patient.diagnosis.setHandspot6(result.handspot);
					num ++;
				}
			} catch(NullPointerException e) {
				//e.printStackTrace();
				continue;
			}
			
		}
		
		for(String str : patient.diagnosis.handspotArr)
		{
			System.out.println("//////////"+str);
		}
		model.addAttribute("patient", patient.getDiagnosis());
		return "hospitalResultPage";
	}
}

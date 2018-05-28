package com.company.dto;

import java.util.ArrayList;
import java.util.List;

public class PatientDiagnosisVO  implements Cloneable{
	private String pNumber; // 환자 번호와 이름을  룰평가 테이블에 넘겨주기 위한 클래스를 만들기 위해 한번 더 기록
	private String pName; 
	private String comment; // 해당 환자가 받은 진단에 대한 룰 평가
	private int score;
	private String visitDate;
	
	private int age; // 세부사항 더 추가 예정
	private String sex;
	
	public ArrayList<String> simplePrescr; // 환자에 증상에 매칭 되는 단순처방
	public ArrayList<String> complexPrescr; // 환자에 증상에 매칭 되는 복합처방
	public ArrayList<String> diagnosisArr; // 환자가 받은 진단명 리스트
	
	public static final int NODIAGNOSISED   = 0;//진료 받은적 없다.
    public static final int DIAGNOSISED = 1; //진료 받은적 있다. 
    
    public int state;
 // public int ruleCheckCnt; // 모든 룰을 다 체크했는지 확인하기 위함
  
	public PatientDiagnosisVO(String visitDate) {
		this.state = NODIAGNOSISED;
		simplePrescr = new ArrayList<String>();
		complexPrescr = new ArrayList<String>();
		diagnosisArr = new ArrayList<String>();
		this.visitDate = visitDate;
		
		
	}
	public Object clone() {
		try {
			PatientDiagnosisVO obj = (PatientDiagnosisVO)super.clone();
			return obj;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}
	
	public void setComplexPrescr(ArrayList<MedicineVO> medicines) // 결과값 처방약들 셋팅
	{
		for(MedicineVO obj : medicines)
		{
			complexPrescr.add(obj.getMedicine()+" "+obj.getDefaultValue());
		}
	}
	public void setSimplePrescr(ArrayList<MedicineVO> medicines) // 결과값 처방약 셋팅 ( 단순 처방이니 배열크기는 1)
	{
		for(MedicineVO obj : medicines)
		{
			simplePrescr.add(obj.getMedicine()+" "+obj.getDefaultValue());
		}
	}
	
	public ArrayList<String> getDiagnosisArr() {
		return diagnosisArr;
	}
	public void setDiagnosisArr(ArrayList<String> diagnosisArr) {
		this.diagnosisArr = diagnosisArr;
	}
	public void addDiagnosisArr(String diagnosis) {
		this.diagnosisArr.add(diagnosis);
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public void setState(int state) {
		this.state = state; 
	}
	public int getState() {
		return state;
	}
	public String getpNumber() {
		return pNumber;
	}
	public void setpNumber(String pNumber) {
		this.pNumber = pNumber;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public ArrayList<String> getSimplePrescr() {
		return simplePrescr;
	}

	public ArrayList<String> getComplexPrescr() {
		return complexPrescr;
	}

	public static int getNodiagnosised() {
		return NODIAGNOSISED;
	}
	public static int getDiagnosised() {
		return DIAGNOSISED;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
package com.company.drools;

import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("DroolsSpringTest")
public class DroolsSpringTest {
	
	@Autowired
	private ApplicationContext applicationContext;
	/**
	 * DomainRule �� map Ÿ������ ���� 
	 */
	
	public static void main(String args[]) {
	}
	/**
	 * ȯ�ڸ� ���� �� �˻��ϱ� ��ư�� ������ ��츸 checkSymptom() ����
	 */
	public void checkSymptom() {
		try {
			// ��� ����
			applicationContext = new ClassPathXmlApplicationContext(
					"drools-context/applicationContext-drools.xml");
			// stateful ����� session �� getbean
			StatefulKnowledgeSession ksession = (StatefulKnowledgeSession) applicationContext
					.getBean("ksession");
			ksession.fireAllRules();
			
		} catch( Throwable e) {
			e.printStackTrace();
		}
	}
}
package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cardpay.pccredit.customer.model.QuestionDict;
import com.cardpay.pccredit.customer.service.CustomerQuestionService;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.service.DictService;

/**
 * Question_Dict类的描述
 * 
 * @author 王海东
 * @created on 2014-11-6
 * 
 * @version $Id:$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-junit.xml")
public class Question_Dict {

	@Autowired
	private DictService dictService;

	@Autowired
	private CustomerQuestionService customerQuestionService;

	@Test
	public void test() {
		/*List<QuestionDict> qd = customerQuestionService.findQuestionDict();
		for (QuestionDict questionDict : qd) {
			String qc = questionDict.getQuestionCode();
			String qcfinall = qc;
			String qn = questionDict.getQuestionName();
			Dict di = new Dict();
			di.setDictType("WJDC-问卷调查");
			di.setTypeCode(qcfinall);
			di.setTypeName(qn);
			dictService.insertDict(di);
	}*/
		}

}

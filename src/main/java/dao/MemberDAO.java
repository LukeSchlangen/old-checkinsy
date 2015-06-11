package main.java.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import main.java.model.Member;
import main.java.util.DAOUtils;
import main.java.util.LoggingUtils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.util.json.JSONArray;

public class MemberDAO {
	
	private static Logger logger;

	private DynamoDBMapper mapper;
	
	public MemberDAO() throws IOException {
		logger = LoggingUtils.getInstance();
		mapper = DAOUtils.setupDatabase();
	}
	
	public void createMember(String name, String group) {
		String id = DAOUtils.getUUID();
		Member member = new Member(id, name, group);
		mapper.save(member);
		
		logger.info(String.format("Added member [%s] with name [%s] to group [%s]", id, name, group));
	}
	
	public JSONArray getUsersForGroup(String group) {
		Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ)
											 .withAttributeValueList(new AttributeValue().withS(group));
		
		DynamoDBScanExpression scan = new DynamoDBScanExpression().withFilterConditionEntry("Group", condition);
		List<Member> result = mapper.scan(Member.class, scan);
		return listToJson(result);		
	}
	
	private JSONArray listToJson(List<Member> members) {
		List<String> names = new ArrayList<>();
		
		for(Member member : members) {
			names.add(member.getName());
		}
		
		return new JSONArray(names);
	}

}

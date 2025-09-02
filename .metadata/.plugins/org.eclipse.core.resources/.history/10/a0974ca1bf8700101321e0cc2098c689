package myspring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// EmpServiceImpl이 EmpDAO를 의존하고 있다.

@Component("empService")
public class EmpServiceImpl implements EmpService {
	
	@Autowired
	EmpDAO dao;
	
	@Override
	public void setDAO(EmpDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<Emp> getEmpListByDeptNo(int deptNo) throws Exception {
		return dao.getEmpListByDeptNo(deptNo);
	}

}

package com.interpretor.types;

public abstract class Data<T>{
	T Data;
	public Data(T Data) {
		this.Data = Data;
	}
	@Override
	public String toString() {
		return (this.Data)+"";
	}
//	public abstract <T> T add(Data DATA);
//	public abstract <T> T sub(Data DATA);
//	public abstract <T> T mul(Data DATA);
//	public abstract <T> T div(Data DATA);
//	
	public static Data add(Data d1, Data d2) {
		Number originData = (Number) d1.Data;
		Number operandData = (Number) d2.Data;
		int priorityDT = Math.max(getDTpriority(originData.getClass().getTypeName()), getDTpriority(operandData.getClass().getTypeName()));
		try {
			return Value.allocateDataType(operate(originData, operandData, '+', priorityDT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Data subtract(Data d1, Data d2) {
		Number originData = (Number) d1.Data;
		Number operandData = (Number) d2.Data;
		int priorityDT = Math.max(getDTpriority(originData.getClass().getTypeName()), getDTpriority(operandData.getClass().getTypeName()));
		try {
			return Value.allocateDataType(operate(originData, operandData, '-', priorityDT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Data multiply(Data d1, Data d2) {
		Number originData = (Number) d1.Data;
		Number operandData = (Number) d2.Data;
		int priorityDT = Math.max(getDTpriority(originData.getClass().getTypeName()), getDTpriority(operandData.getClass().getTypeName()));
		try {
			return Value.allocateDataType(operate(originData, operandData, '*', priorityDT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Data divide(Data d1, Data d2) {
		Number originData = (Number) d1.Data;
		Number operandData = (Number) d2.Data;
		int priorityDT = Math.max(getDTpriority(originData.getClass().getTypeName()), getDTpriority(operandData.getClass().getTypeName()));
		try {
			return Value.allocateDataType(operate(originData, operandData, '/', priorityDT));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static int getDTpriority(String TYPE) {
		switch(TYPE) {
			case "java.lang.Integer":
				return 1;
			case "java.lang.Long":
				return 2;
			case "java.lang.Float":
				return 3;
			case "java.lang.Double":
				return 4;
			default: return -1;
		}
	}
	public static Number operate(Number n1, Number n2, char operator, int parsingDT) throws Exception {
		switch(parsingDT) {
			case 1:
				switch(operator) {
				case '+':
					return new Integer((n1.intValue() + n2.intValue()));
				case '-':
					return new Integer((n1.intValue() - n2.intValue()));
				case '*':
					return new Integer((n1.intValue() * n2.intValue()));
				case '/':
					return new Integer((n1.intValue() / n2.intValue()));
				}
				break;
			case 2:
				switch(operator) {
				case '+':
					return new Long((n1.longValue() + n2.longValue()));
				case '-':
					return new Long((n1.longValue() - n2.longValue()));
				case '*':
					return new Long((n1.longValue() * n2.longValue()));
				case '/':
					return new Long((n1.longValue() / n2.longValue()));
				}
				break;
			case 3:
				switch(operator) {
				case '+':
					return new Float((n1.floatValue() + n2.floatValue()));
				case '-':
					return new Float((n1.floatValue() - n2.floatValue()));
				case '*':
					return new Float((n1.floatValue() * n2.floatValue()));
				case '/':
					return new Float((n1.floatValue() / n2.floatValue()));
				}
				break;
			case 4:
				switch(operator) {
				case '+':
					return new Double((n1.doubleValue() + n2.doubleValue()));
				case '-':
					return new Double((n1.doubleValue() - n2.doubleValue()));
				case '*':
					return new Double((n1.doubleValue() * n2.doubleValue()));
				case '/':
					return new Double((n1.doubleValue() / n2.doubleValue()));
				}
				break;
			default:
				throw new Exception();
		}
		return null;
	}
}
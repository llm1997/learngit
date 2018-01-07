package unix.knn;

public class People {
	private long id;
    private double one;
    private double two;
    private double three;
    private double four;
    private double five;
    private String type;
    
    public People(long id,double one,double two,double three,double four,double five,String type) {
		this.id=id;
		this.one=one;
		this.three=three;
		this.two=two;
		this.four=four;
		this.type=type;
		this.five=five;
	}
    public People(long id,double one,double two,double three,double four,double five) {
		this.id=id;
		this.one=one;
		this.three=three;
		this.two=two;
		this.four=four;
		this.five=five;
	}
    
    public People() {
		
	}
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getOne() {
		return one;
	}
	public void setOne(double one) {
		this.one = one;
	}
	public double getTwo() {
		return two;
	}
	public void setTwo(double two) {
		this.two = two;
	}
	public double getThree() {
		return three;
	}
	public void setThree(double three) {
		this.three = three;
	}
	public double getFour() {
		return four;
	}
	public void setFour(double four) {
		this.four = four;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getFive() {
		return five;
	}
	public void setFive(double five) {
		this.five = five;
	}
    
    
}

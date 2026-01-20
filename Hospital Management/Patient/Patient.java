public class Patient extends Person  {
    private String patientId;
    private String condition;

    public Patient(String name, int age, String patientId, String condition) {
        super(name, age);
        this.patientId = patientId;
        this.condition = condition;
    }

        public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientId() {
        return patientId;
    }

     public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getcondition() {
        return condition;
    }

    public String getDetails() {
       
        return "Patient: " + name + ", Age: " + age + ", ID: " + patientId + ", Condition: " + condition;
    }
}

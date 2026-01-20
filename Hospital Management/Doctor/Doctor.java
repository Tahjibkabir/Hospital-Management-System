public class Doctor extends Person  {
    private String doctorId;
    private String speciality;

    public Doctor(String name, int age, String doctorId, String speciality) {
        super(name, age);
        this.doctorId = doctorId;
        this.speciality = speciality;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorId() {
        return doctorId;
    }

     public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getSpeciality() {
        return speciality;
    }


    @Override
    public String getDetails() {

        return "Doctor: " + name + ", Age: " + age + ", ID: " + doctorId + ", Speciality: " + speciality;
    }

   
}
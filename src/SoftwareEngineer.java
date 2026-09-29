/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

/**
 * Kỹ sư phần mềm: một {@link Employee} có thêm ngôn ngữ lập trình chính
 * và phụ cấp kỹ thuật.
 * <p>
 * Bất biến: ngôn ngữ chính không rỗng, phụ cấp không âm.
 */
public class SoftwareEngineer extends Employee {
    private String primaryLanguage;
    private double technicalAllowance;

    /**
     * Tạo kỹ sư với lương cơ bản và phụ cấp bằng 0.
     *
     * @param id              mã nhân sự
     * @param fullName        họ tên
     * @param primaryLanguage ngôn ngữ lập trình chính, không rỗng
     */
    public SoftwareEngineer(String id, String fullName, String primaryLanguage) {
        this(id, fullName, 0, primaryLanguage, 0);
    }

    /**
     * Tạo kỹ sư đầy đủ thông tin.
     *
     * @param id                 mã nhân sự
     * @param fullName           họ tên
     * @param baseSalary         lương cơ bản, không âm
     * @param primaryLanguage    ngôn ngữ lập trình chính, không rỗng
     * @param technicalAllowance phụ cấp kỹ thuật, không âm
     * @throws IllegalArgumentException nếu vi phạm ràng buộc
     */
    public SoftwareEngineer(String id, String fullName, double baseSalary,
                            String primaryLanguage, double technicalAllowance) {
        super(id, fullName, baseSalary);
        if (primaryLanguage == null || primaryLanguage.trim().isEmpty()) {
            throw new IllegalArgumentException("Ngon ngu chinh khong duoc rong");
        }
        if (technicalAllowance < 0) {
            throw new IllegalArgumentException("Phu cap ky thuat khong duoc am");
        }
        this.primaryLanguage = primaryLanguage;
        this.technicalAllowance = technicalAllowance;
    }

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public double getTechnicalAllowance() {
        return technicalAllowance;
    }

    /** Chi phí hằng tháng = lương cơ bản + phụ cấp kỹ thuật. */
    @Override
    public double calculateMonthlyCost() {
        return getBaseSalary() + technicalAllowance;
    }

    @Override
    public void displayInfo() {
        System.out.printf("Ky su: %s - %s, ngon ngu: %s, luong co ban: %.0f, phu cap: %.0f, chi phi thang: %.0f%n",
                getId(), getFullName(), primaryLanguage, getBaseSalary(),
                technicalAllowance, calculateMonthlyCost());
    }

    /** In thông báo của lớp con trước, rồi gọi lớp cha (giống thứ tự destructor C++). */
    @Override
    public void close() {
        System.out.println("Huy SoftwareEngineer " + getId());
        super.close();
    }
}

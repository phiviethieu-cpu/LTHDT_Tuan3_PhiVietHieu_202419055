/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

/**
 * Nhân sự thông thường của đơn vị.
 * <p>
 * Một nhân sự tồn tại độc lập với dự án và có thể được phân công vào nhiều
 * {@link ProjectTeam} khác nhau.
 * <p>
 * Bất biến: mã và họ tên không rỗng, lương cơ bản không âm.
 * <p>
 * Java không có destructor nên lớp cài đặt {@link AutoCloseable};
 * {@link #close()} in thông báo để quan sát vòng đời đối tượng.
 */
public class Employee implements AutoCloseable {
    private String id;
    private String fullName;
    private double baseSalary;

    /** Tạo nhân sự với giá trị mặc định: "UNKNOWN", "Unnamed employee", lương 0. */
    public Employee() {
        this("UNKNOWN", "Unnamed employee", 0);
    }

    /**
     * Tạo nhân sự có lương cơ bản bằng 0.
     *
     * @param id       mã nhân sự, không rỗng
     * @param fullName họ tên, không rỗng
     */
    public Employee(String id, String fullName) {
        this(id, fullName, 0);
    }

    /**
     * Tạo nhân sự đầy đủ thông tin.
     *
     * @param id         mã nhân sự, không rỗng
     * @param fullName   họ tên, không rỗng
     * @param baseSalary lương cơ bản, không âm
     * @throws IllegalArgumentException nếu vi phạm ràng buộc
     */
    public Employee(String id, String fullName, double baseSalary) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma nhan su khong duoc rong");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ho ten khong duoc rong");
        }
        if (baseSalary < 0) {
            throw new IllegalArgumentException("Luong co ban khong duoc am");
        }
        this.id = id;
        this.fullName = fullName;
        this.baseSalary = baseSalary;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    /**
     * Tăng lương theo một số tiền cố định.
     *
     * @param amount số tiền tăng, phải dương
     */
    public void increaseSalary(double amount) {
        increaseSalary(amount, false);
    }

    /**
     * Tăng lương theo phần trăm hoặc theo số tiền cố định.
     *
     * @param value        giá trị tăng, phải dương
     * @param byPercentage {@code true}: tăng {@code value}%; {@code false}: tăng {@code value} đồng
     * @throws IllegalArgumentException nếu {@code value <= 0}
     */
    public void increaseSalary(double value, boolean byPercentage) {
        if (value <= 0) {
            throw new IllegalArgumentException("Gia tri tang luong phai duong");
        }
        if (byPercentage) {
            baseSalary = baseSalary + baseSalary * value / 100;
        } else {
            baseSalary = baseSalary + value;
        }
    }

    /**
     * Chi phí nhân sự hằng tháng; mặc định bằng lương cơ bản.
     * Lớp con ghi đè để cộng thêm các khoản khác.
     */
    public double calculateMonthlyCost() {
        return baseSalary;
    }

    /** In thông tin nhân sự; lớp con ghi đè để in thêm thuộc tính riêng. */
    public void displayInfo() {
        System.out.printf("Nhan su: %s - %s, luong co ban: %.0f, chi phi thang: %.0f%n",
                id, fullName, baseSalary, calculateMonthlyCost());
    }

    /** Thay cho destructor: in thông báo khi đối tượng kết thúc vòng đời. */
    @Override
    public void close() {
        System.out.println("Huy Employee " + id);
    }
}

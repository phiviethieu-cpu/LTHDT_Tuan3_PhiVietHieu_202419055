/****************/
// Mã sinh viên: 202419055
// Họ tên: Phí Việt Hiếu
/****************/

import java.util.ArrayList;

/**
 * Nhóm dự án gồm một trưởng nhóm và danh sách thành viên.
 * <p>
 * Quan hệ với {@link Employee} là kết tập (aggregation): nhóm chỉ giữ tham chiếu,
 * không sở hữu nhân sự. {@link #close()} chỉ xóa danh sách nội bộ,
 * không hủy các đối tượng {@code Employee}.
 * <p>
 * Bất biến:
 * <ul>
 *   <li>Không có hai thành viên cùng mã trong một nhóm.</li>
 *   <li>Trưởng nhóm (nếu có) luôn nằm trong danh sách thành viên, và chỉ xuất hiện một lần.</li>
 * </ul>
 * Thao tác vi phạm bất biến trả về {@code false} và nhóm giữ nguyên trạng thái.
 */
public class ProjectTeam implements AutoCloseable {
    private String projectCode;
    private String projectName;
    private Employee leader; // null nếu chưa có trưởng nhóm
    private ArrayList<Employee> members = new ArrayList<>();

    /**
     * Tạo nhóm chưa có trưởng nhóm.
     *
     * @param projectCode mã dự án, không rỗng
     * @param projectName tên dự án, không rỗng
     */
    public ProjectTeam(String projectCode, String projectName) {
        if (projectCode == null || projectCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma du an khong duoc rong");
        }
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten du an khong duoc rong");
        }
        this.projectCode = projectCode;
        this.projectName = projectName;
    }

    /**
     * Tạo nhóm, đặt trưởng nhóm và tự động thêm trưởng nhóm vào danh sách thành viên.
     *
     * @param leader trưởng nhóm, khác null
     */
    public ProjectTeam(String projectCode, String projectName, Employee leader) {
        this(projectCode, projectName);
        if (leader == null) {
            throw new IllegalArgumentException("Truong nhom khong duoc null");
        }
        members.add(leader);
        this.leader = leader;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public Employee getLeader() {
        return leader;
    }

    public int getMemberCount() {
        return members.size();
    }

    /** Tìm thành viên theo mã; trả về null nếu không có. */
    private Employee findById(String employeeId) {
        for (Employee e : members) {
            if (e.getId().equals(employeeId)) {
                return e;
            }
        }
        return null;
    }

    /** Kiểm tra nhóm có thành viên mang mã {@code employeeId} hay không. */
    public boolean contains(String employeeId) {
        return findById(employeeId) != null;
    }

    /**
     * Thêm một thành viên.
     *
     * @return {@code false} nếu null hoặc đã có thành viên cùng mã
     */
    public boolean addMember(Employee employee) {
        if (employee == null || contains(employee.getId())) {
            return false;
        }
        members.add(employee);
        return true;
    }

    /**
     * Thêm một thành viên, có thể đặt làm trưởng nhóm.
     * Trưởng nhóm cũ vẫn là thành viên.
     * Nếu nhân sự đã có trong nhóm thì trả về {@code false};
     * muốn đổi trưởng nhóm cho thành viên sẵn có thì dùng {@link #changeLeader(Employee)}.
     *
     * @param makeLeader {@code true} để đặt nhân sự vừa thêm làm trưởng nhóm
     */
    public boolean addMember(Employee employee, boolean makeLeader) {
        if (!addMember(employee)) {
            return false;
        }
        if (makeLeader) {
            leader = employee;
        }
        return true;
    }

    /**
     * Xóa thành viên theo mã.
     *
     * @return {@code false} nếu không tìm thấy, hoặc đó là trưởng nhóm hiện tại
     *         (phải đổi trưởng nhóm trước khi xóa)
     */
    public boolean removeMember(String employeeId) {
        Employee found = findById(employeeId);
        if (found == null) {
            return false;
        }
        if (found == leader) {
            return false;
        }
        members.remove(found);
        return true;
    }

    /**
     * Đổi trưởng nhóm. Nếu nhân sự chưa thuộc nhóm thì được thêm vào trước.
     *
     * @return {@code false} nếu null, hoặc trong nhóm đã có một người khác dùng cùng mã
     */
    public boolean changeLeader(Employee employee) {
        if (employee == null) {
            return false;
        }
        Employee found = findById(employee.getId());
        if (found == null) {
            members.add(employee);
        } else if (found != employee) {
            return false;
        }
        leader = employee;
        return true;
    }

    /** Tổng chi phí hằng tháng; gọi đa hình {@link Employee#calculateMonthlyCost()}. */
    public double calculateTotalMonthlyCost() {
        double total = 0;
        for (Employee e : members) {
            total += e.calculateMonthlyCost();
        }
        return total;
    }

    /** In thông tin nhóm; mỗi thành viên hiển thị qua lời gọi đa hình {@code displayInfo()}. */
    public void displayTeam() {
        System.out.println("Nhom " + projectCode + " - " + projectName);
        if (leader == null) {
            System.out.println("Truong nhom: chua co");
        } else {
            System.out.println("Truong nhom: " + leader.getId() + " - " + leader.getFullName());
        }
        System.out.println("So thanh vien: " + members.size());
        for (Employee e : members) {
            e.displayInfo();
        }
    }

    /** Chỉ hủy cấu trúc liên kết nội bộ; các Employee vẫn tồn tại. */
    @Override
    public void close() {
        System.out.println("Huy ProjectTeam " + projectCode + " (cac nhan su van con)");
        members.clear();
        leader = null;
    }
}

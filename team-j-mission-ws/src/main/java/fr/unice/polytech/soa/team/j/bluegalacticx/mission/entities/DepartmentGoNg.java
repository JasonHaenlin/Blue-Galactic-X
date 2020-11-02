package fr.unice.polytech.soa.team.j.bluegalacticx.mission.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.unice.polytech.soa.team.j.bluegalacticx.rocket.proto.GoNogoRequest.Department;

public class DepartmentGoNg {
    Map<Department, Boolean> departments = new HashMap<>();

    public DepartmentGoNg() {
        departments.put(Department.MISSION, false);
    }

    public DepartmentGoNg(Map<Department, Boolean> departments) {
        this.departments = departments;
    }

    public Map<Department, Boolean> getDepartments() {
        return this.departments;
    }

    public void setDepartments(Map<Department, Boolean> departments) {
        this.departments = departments;
    }

    public void updateGoNogo(Department department, boolean status) {
        this.departments.put(department, status);
    }

    public DepartmentGoNg departments(Map<Department, Boolean> departments) {
        this.departments = departments;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DepartmentGoNg)) {
            return false;
        }
        DepartmentGoNg departmentGoNg = (DepartmentGoNg) o;
        return Objects.equals(departments, departmentGoNg.departments);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(departments);
    }

    @Override
    public String toString() {
        return "{" + " departments='" + getDepartments() + "'" + "}";
    }

}

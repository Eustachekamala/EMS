// Department Types
export type DepartmentType =
    | 'HUMAN_RESOURCES'
    | 'FINANCE'
    | 'IT'
    | 'OPERATIONS'
    | 'MARKETING'
    | 'SALES';

export interface Department {
    departmentId: number;
    name: DepartmentType;
}

// Job Types
export interface Job {
    jobId: number;
    title: string;
    description: string;
    salary: number;
    department: Department;
}

export interface JobRequest {
    title: string;
    description: string;
    salary: number;
    departmentId: number;
}

export interface JobResponse {
    jobId: number;
    title: string;
    description: string;
    salary: number;
    departmentName: string;
    departmentId: number;
}

// Employee Types
export interface Employee {
    id: number;
    firstname: string;
    lastname: string;
    email: string;
    phone: string;
    hireDate: string;
    birthDate: string;
    gender: string;
    street: string;
    city: string;
    country: string;
    zipcode: string;
    rfidTag?: string;
    dailyRate: number;
    department?: Department;
    job?: Job;
}

export interface EmployeeRequest {
    firstname: string;
    lastname: string;
    email: string;
    password?: string;
    phone: string;
    hireDate: string;
    dob: string;
    gender: string;
    street: string;
    city: string;
    country: string;
    zipcode: string;
    rfidTag?: string;
    dailyRate: number;
    departmentId?: number;
    jobId?: number;
}

export interface UpdateEmployeeRequest {
    firstname?: string;
    lastname?: string;
    email?: string;
    phone?: string;
    hireDate?: string;
    birthDate?: string;
    gender?: string;
    street?: string;
    city?: string;
    country?: string;
    zipcode?: string;
    rfidTag?: string;
    dailyRate?: number;
    departmentId?: number;
    jobId?: number;
}

export interface EmployeeResponse {
    id: number;
    firstname: string;
    lastname: string;
    email: string;
    phone: string;
    hireDate: string;
    birthDate: string;
    gender: string;
    street: string;
    city: string;
    country: string;
    zipcode: string;
    rfidTag?: string;
    dailyRate: number;
    departmentName?: string;
    departmentId?: number;
    jobTitle?: string;
    jobId?: number;
}

// Attendance Types
export interface Attendance {
    attendanceId: number;
    attendanceDate: string;
    checkInTime: string | null;
    checkOutTime: string | null;
    attendanceStatus: string;
    employee: Employee;
}

export interface AttendanceResponse {
    attendanceId: number;
    attendanceDate: string;
    checkInTime: string | null;
    checkOutTime: string | null;
    attendanceStatus: string;
    employeeId: number;
    employeeName: string;
}

// Payroll Types
export interface Payroll {
    payrollId: number;
    salary: number;
    paymentDate: string;
    payrollYear: number;
    payrollMonth: number;
    employee: Employee;
}

export interface PayrollResponse {
    payrollId: number;
    salary: number;
    paymentDate: string;
    payrollYear: number;
    payrollMonth: number;
    employeeId: number;
    employeeName: string;
}

export interface PayrollHistory {
    payrollId: number;
    employeeName: string;
    jobTitle: string;
    departmentName: string;
    salary: number;
    paymentDate: string;
    year: number;
    month: number;
}

// API Response Types
export interface ApiError {
    message: string;
    status?: number;
}

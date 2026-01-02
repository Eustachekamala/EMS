import type { InternalAxiosRequestConfig } from 'axios';
import db from '../data/mock_db.json';

// Helper to simulate network delay
const delay = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));

// Helper to create a successful response
const successResponse = (data: any) => ({
    data,
    status: 200,
    statusText: 'OK',
    headers: {},
    config: {} as InternalAxiosRequestConfig,
});

// Helper to create an error response
const errorResponse = (message: string, status: number = 404) => Promise.reject({
    response: {
        data: { message },
        status,
        statusText: 'Error',
        headers: {},
        config: {} as InternalAxiosRequestConfig,
    }
});

export const mockApi = {
    get: async (url: string) => {
        await delay(300); // Simulate latency

        // Employees
        if (url === '/employees/all') {
            const employees = db.employees.map((e: any) => ({
                ...e,
                departmentName: e.department?.name,
                jobTitle: e.job?.title
            }));
            return successResponse(employees);
        }
        if (url.match(/\/employees\/\d+$/)) {
            const id = parseInt(url.split('/').pop() || '0');
            const employee = db.employees.find((e: any) => e.id === id);
            if (!employee) return errorResponse('Employee not found');

            return successResponse({
                ...employee,
                departmentName: employee.department?.name,
                jobTitle: employee.job?.title
            });
        }
        if (url.match(/\/employees\/rfid\/.+$/)) {
            const rfid = url.split('/').pop();
            const employee = db.employees.find((e: any) => e.rfidTag === rfid);
            return employee ? successResponse(employee) : errorResponse('Employee not found');
        }

        // Departments
        if (url === '/departments/all') {
            return successResponse(db.departments);
        }
        // Jobs
        if (url === '/jobs/all') {
            return successResponse(db.jobs);
        }

        // Payrolls
        if (url === '/payrolls/all' || url === '/payroll/all') {
            const allPayrolls = db.employees.flatMap((e: any) => e.payrolls.map((p: any) => ({
                payrollId: p.payrollId,
                employeeName: `${e.firstname} ${e.lastname}`,
                jobTitle: e.job?.title || 'N/A',
                departmentName: e.department?.name || 'N/A',
                salary: p.salary,
                paymentDate: p.paymentDate,
                year: p.payrollYear,
                month: p.payrollMonth
            })));
            return successResponse(allPayrolls);
        }
        if (url.match(/\/payroll\/employee\/\d+$/)) {
            const id = parseInt(url.split('/').pop() || '0');
            const employee = db.employees.find((e: any) => e.id === id);
            if (!employee) return successResponse([]);

            const employeePayrolls = employee.payrolls.map((p: any) => ({
                payrollId: p.payrollId,
                salary: p.salary,
                paymentDate: p.paymentDate,
                payrollYear: p.payrollYear,
                payrollMonth: p.payrollMonth,
                employeeId: employee.id,
                employeeName: `${employee.firstname} ${employee.lastname}`
            }));
            return successResponse(employeePayrolls);
        }

        // Attendance History
        if (url === '/attendance/history') {
            const allAttendance = db.employees.flatMap((e: any) => e.attendances.map((a: any) => ({
                ...a,
                employeeId: e.id,
                employeeName: e.firstname + ' ' + e.lastname,
                departmentName: e.department.name
            })));
            return successResponse(allAttendance);
        }

        return errorResponse('Mock endpoint not found', 404);
    },

    post: async (url: string, data: any) => {
        await delay(300);
        console.log('Mock POST request:', url, data);

        if (url === '/auth/login') {
            const user = db.users.find((u: any) => u.username === data.username);
            if (user) {
                return successResponse({ token: "mock-token-123", ...user });
            }
            if (data.username === 'admin') {
                return successResponse({ token: "mock-token-admin", username: 'admin', role: 'ADMIN' });
            }
            return errorResponse("Invalid credentials", 401);
        }

        if (url.match(/\/payroll\/generate\/\d+\/\d+\/\d+$/)) {
            return successResponse({ message: "Payroll generated successfully (Mock)" });
        }

        return successResponse({ message: 'Operation successful (Mock)' });
    },

    patch: async (url: string, data: any) => {
        await delay(300);
        console.log('Mock PATCH request:', url, data);
        return successResponse({ message: 'Update successful (Mock)' });
    },

    delete: async (url: string) => {
        await delay(300);
        console.log('Mock DELETE request:', url);
        return successResponse({ message: 'Delete successful (Mock)' });
    }
};

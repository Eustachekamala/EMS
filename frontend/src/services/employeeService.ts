import api from '@/lib/api';
import type { EmployeeResponse, EmployeeRequest, UpdateEmployeeRequest, AttendanceResponse } from '@/types';

export const employeeService = {
    async getAllEmployees(): Promise<EmployeeResponse[]> {
        const response = await api.get('/employees/all');
        return response.data;
    },

    async getEmployeeById(id: number): Promise<EmployeeResponse> {
        const response = await api.get(`/employees/${id}`);
        return response.data;
    },

    async getEmployeeByRfid(rfid: string): Promise<EmployeeResponse> {
        const response = await api.get(`/employees/rfid/${rfid}`);
        return response.data;
    },

    async createEmployee(employee: EmployeeRequest): Promise<string> {
        const response = await api.post('/employees/insert', employee);
        return response.data;
    },

    async updateEmployee(id: number, employee: UpdateEmployeeRequest): Promise<string> {
        const response = await api.patch(`/employees/update/${id}`, employee);
        return response.data;
    },

    async deleteEmployee(id: number): Promise<string> {
        const response = await api.delete(`/employees/delete/${id}`);
        return response.data;
    },

    async scanRfid(uid: string): Promise<AttendanceResponse> {
        const response = await api.post('/employees/scan', { uid });
        return response.data;
    },

    async searchByFirstname(firstname: string): Promise<EmployeeResponse> {
        const response = await api.get(`/employees/search/${firstname}`);
        return response.data;
    },

    async searchByLastname(lastname: string): Promise<EmployeeResponse> {
        const response = await api.get(`/employees/search/lastname/${lastname}`);
        return response.data;
    },
};

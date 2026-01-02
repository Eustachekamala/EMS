import api from '@/lib/api';
import type { Payroll, PayrollResponse } from '@/types';

export const payrollService = {
    async generatePayroll(employeeId: number, year: number, month: number): Promise<Payroll> {
        const response = await api.post(`/payroll/generate/${employeeId}/${year}/${month}`);
        return response.data;
    },

    async getEmployeePayrolls(employeeId: number): Promise<PayrollResponse[]> {
        const response = await api.get(`/payroll/employee/${employeeId}`);
        return response.data;
    },

    async getAllPayrolls(): Promise<import('@/types').PayrollHistory[]> {
        const response = await api.get('/payroll/all');
        return response.data;
    },
};

import api from '@/lib/api';
import type { AttendanceResponse } from '@/types';

export const attendanceService = {
    async getAttendanceHistory(): Promise<AttendanceResponse[]> {
        const response = await api.get('/attendance/history');
        return response.data;
    },
};

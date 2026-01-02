import { useEffect, useState } from 'react';
import { Calendar, UserCheck, UserX, Scan, Search } from 'lucide-react';
import toast from 'react-hot-toast';
import Layout from '@/components/layout/Layout';
import Card from '@/components/common/Card';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import LoadingSpinner from '@/components/common/LoadingSpinner';
import Modal from '@/components/common/Modal';
import { attendanceService } from '@/services/attendanceService';
import { employeeService } from '@/services/employeeService';
import type { AttendanceResponse } from '@/types';
import { formatDate, formatTime } from '@/utils/formatters';

export default function Attendance() {
    const [attendanceRecords, setAttendanceRecords] = useState<AttendanceResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [scanModalOpen, setScanModalOpen] = useState(false);
    const [rfidInput, setRfidInput] = useState('');
    const [scanning, setScanning] = useState(false);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        fetchAttendance();
    }, []);

    const fetchAttendance = async () => {
        try {
            const data = await attendanceService.getAttendanceHistory();
            setAttendanceRecords(data);
        } catch (error) {
            console.error('Failed to fetch attendance:', error);
            toast.error('Failed to load attendance records');
        } finally {
            setLoading(false);
        }
    };

    const handleScan = async () => {
        if (!rfidInput.trim()) return;
        setScanning(true);
        const toastId = toast.loading('Scanning RFID...');
        try {
            const result = await employeeService.scanRfid(rfidInput);
            await fetchAttendance();
            setScanModalOpen(false);
            setRfidInput('');
            toast.success(`Attendance recorded for ${result.employeeName}`, { id: toastId });
        } catch (error: any) {
            toast.error(error.message || 'Failed to scan RFID', { id: toastId });
        } finally {
            setScanning(false);
        }
    };

    const filteredRecords = attendanceRecords.filter(record =>
        record.employeeName.toLowerCase().includes(searchTerm.toLowerCase())
    );

    if (loading) {
        return (
            <Layout>
                <div className="flex items-center justify-center h-[60vh]">
                    <LoadingSpinner size="lg" />
                </div>
            </Layout>
        );
    }

    return (
        <Layout>
            <div className="space-y-6 animate-fade-in">
                {/* Header */}
                <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
                    <div>
                        <h1 className="text-3xl font-bold gradient-text">Attendance Tracking</h1>
                        <p className="text-gray-400 mt-1">{attendanceRecords.length} records found</p>
                    </div>
                    <Button onClick={() => setScanModalOpen(true)}>
                        <Scan className="h-5 w-5" />
                        Scan RFID
                    </Button>
                </div>

                {/* Search */}
                <Card className="!p-4">
                    <div className="relative">
                        <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400" />
                        <Input
                            placeholder="Search by employee name..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="pl-10 !mb-0"
                        />
                    </div>
                </Card>

                {/* Desktop Table View */}
                <div className="hidden md:block">
                    <Card>
                        <div className="overflow-x-auto">
                            <table className="w-full">
                                <thead>
                                    <tr className="border-b border-white/10">
                                        <th className="text-left py-3 px-4 text-gray-400 font-medium">Employee</th>
                                        <th className="text-left py-3 px-4 text-gray-400 font-medium">Date</th>
                                        <th className="text-left py-3 px-4 text-gray-400 font-medium">Check In</th>
                                        <th className="text-left py-3 px-4 text-gray-400 font-medium">Check Out</th>
                                        <th className="text-left py-3 px-4 text-gray-400 font-medium">Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {filteredRecords.length > 0 ? (
                                        filteredRecords.map((record) => (
                                            <tr key={record.attendanceId} className="border-b border-white/5 hover:bg-white/5 transition-colors">
                                                <td className="py-3 px-4">
                                                    <div className="flex items-center gap-2">
                                                        <div className="w-8 h-8 rounded-full bg-gradient-to-br from-primary-500 to-primary-700 flex items-center justify-center text-white text-sm font-bold">
                                                            {record.employeeName.charAt(0)}
                                                        </div>
                                                        <span className="text-white font-medium">{record.employeeName}</span>
                                                    </div>
                                                </td>
                                                <td className="py-3 px-4 text-gray-300">
                                                    <div className="flex items-center gap-2">
                                                        <Calendar className="h-4 w-4 text-gray-400" />
                                                        {formatDate(record.attendanceDate)}
                                                    </div>
                                                </td>
                                                <td className="py-3 px-4">
                                                    {record.checkInTime ? (
                                                        <div className="flex items-center gap-2 text-emerald-400">
                                                            <UserCheck className="h-4 w-4" />
                                                            {formatTime(record.checkInTime)}
                                                        </div>
                                                    ) : <span className="text-gray-500">-</span>}
                                                </td>
                                                <td className="py-3 px-4">
                                                    {record.checkOutTime ? (
                                                        <div className="flex items-center gap-2 text-blue-400">
                                                            <UserX className="h-4 w-4" />
                                                            {formatTime(record.checkOutTime)}
                                                        </div>
                                                    ) : <span className="text-gray-500">-</span>}
                                                </td>
                                                <td className="py-3 px-4">
                                                    <StatusBadge status={record.attendanceStatus} />
                                                </td>
                                            </tr>
                                        ))
                                    ) : (
                                        <tr>
                                            <td colSpan={5} className="py-12 text-center text-gray-400">
                                                No attendance records found
                                            </td>
                                        </tr>
                                    )}
                                </tbody>
                            </table>
                        </div>
                    </Card>
                </div>

                {/* Mobile Card View */}
                <div className="md:hidden space-y-4">
                    {filteredRecords.length > 0 ? (
                        filteredRecords.map((record) => (
                            <Card key={record.attendanceId} className="!p-4">
                                <div className="flex justify-between items-start mb-4">
                                    <div className="flex items-center gap-3">
                                        <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary-500 to-primary-700 flex items-center justify-center text-white font-bold">
                                            {record.employeeName.charAt(0)}
                                        </div>
                                        <div>
                                            <h3 className="font-bold text-white">{record.employeeName}</h3>
                                            <div className="flex items-center gap-1.5 text-xs text-gray-400 mt-1">
                                                <Calendar className="h-3 w-3" />
                                                {formatDate(record.attendanceDate)}
                                            </div>
                                        </div>
                                    </div>
                                    <StatusBadge status={record.attendanceStatus} />
                                </div>

                                <div className="grid grid-cols-2 gap-4 border-t border-white/10 pt-4">
                                    <div>
                                        <p className="text-xs text-gray-400 mb-1">Check In</p>
                                        {record.checkInTime ? (
                                            <div className="flex items-center gap-1.5 text-emerald-400 font-medium">
                                                <UserCheck className="h-4 w-4" />
                                                {formatTime(record.checkInTime)}
                                            </div>
                                        ) : <span className="text-gray-500">-</span>}
                                    </div>
                                    <div className="text-right">
                                        <p className="text-xs text-gray-400 mb-1">Check Out</p>
                                        {record.checkOutTime ? (
                                            <div className="flex items-center gap-1.5 text-blue-400 font-medium justify-end">
                                                <UserX className="h-4 w-4" />
                                                {formatTime(record.checkOutTime)}
                                            </div>
                                        ) : <span className="text-gray-500">-</span>}
                                    </div>
                                </div>
                            </Card>
                        ))
                    ) : (
                        <Card>
                            <p className="text-gray-400 text-center py-8">No records found</p>
                        </Card>
                    )}
                </div>
            </div>

            {/* Scan RFID Modal */}
            <Modal
                isOpen={scanModalOpen}
                onClose={() => {
                    setScanModalOpen(false);
                    setRfidInput('');
                }}
                title="Scan RFID Tag"
                size="sm"
            >
                <div className="space-y-4">
                    <p className="text-gray-300">
                        Enter or scan the employee's RFID tag to record attendance.
                    </p>
                    <Input
                        label="RFID Tag"
                        value={rfidInput}
                        onChange={(e) => setRfidInput(e.target.value)}
                        placeholder="Click here and scan tag..."
                        autoFocus
                    />
                    <div className="flex gap-3 justify-end">
                        <Button variant="secondary" onClick={() => setScanModalOpen(false)}>
                            Cancel
                        </Button>
                        <Button onClick={handleScan} isLoading={scanning}>
                            Record Attendance
                        </Button>
                    </div>
                </div>
            </Modal>
        </Layout>
    );
}

function StatusBadge({ status }: { status: string }) {
    const styles = status === 'CHECK_IN'
        ? 'bg-emerald-500/20 text-emerald-400 border-emerald-500/20'
        : 'bg-blue-500/20 text-blue-400 border-blue-500/20';

    return (
        <span className={`inline-block px-3 py-1 rounded-full text-xs font-medium border ${styles}`}>
            {status}
        </span>
    );
}

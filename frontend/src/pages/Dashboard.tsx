import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Users, Clock, DollarSign, Briefcase, TrendingUp, ArrowRight } from 'lucide-react';
import Layout from '@/components/layout/Layout';
import Card from '@/components/common/Card';
import LoadingSpinner from '@/components/common/LoadingSpinner';
import { employeeService } from '@/services/employeeService';
import { attendanceService } from '@/services/attendanceService';
import type { EmployeeResponse, AttendanceResponse } from '@/types';
import { formatDate, formatTime } from '@/utils/formatters';

export default function Dashboard() {
    const [employees, setEmployees] = useState<EmployeeResponse[]>([]);
    const [recentAttendance, setRecentAttendance] = useState<AttendanceResponse[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [employeesData, attendanceData] = await Promise.all([
                    employeeService.getAllEmployees(),
                    attendanceService.getAttendanceHistory(),
                ]);
                setEmployees(employeesData);
                setRecentAttendance(attendanceData.slice(0, 5));
            } catch (error) {
                console.error('Failed to fetch dashboard data:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    if (loading) {
        return (
            <Layout>
                <div className="flex items-center justify-center h-[60vh]">
                    <LoadingSpinner size="lg" />
                </div>
            </Layout>
        );
    }

    const stats = [
        { label: 'Total Employees', value: employees.length, icon: Users, color: 'from-blue-500 to-cyan-500', link: '/employees' },
        { label: 'Active Today', value: recentAttendance.length, icon: Clock, color: 'from-emerald-500 to-teal-500', link: '/attendance' },
        { label: 'Departments', value: new Set(employees.map(e => e.departmentName).filter(Boolean)).size, icon: Briefcase, color: 'from-purple-500 to-pink-500', link: '/jobs' },
        { label: 'Growth', value: '+12%', icon: TrendingUp, color: 'from-amber-500 to-orange-500', link: '/payroll' },
    ];

    return (
        <Layout>
            <div className="space-y-8 animate-fade-in">
                {/* Header */}
                <div>
                    <h1 className="text-4xl font-bold gradient-text mb-2">Welcome to EMS</h1>
                    <p className="text-gray-400">Manage your workforce efficiently</p>
                </div>

                {/* Stats Grid */}
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
                    {stats.map((stat, index) => {
                        const Icon = stat.icon;
                        return (
                            <Link key={index} to={stat.link}>
                                <Card hover className="group">
                                    <div className="flex items-center justify-between">
                                        <div>
                                            <p className="text-gray-400 text-sm mb-1">{stat.label}</p>
                                            <p className="text-3xl font-bold text-white">{stat.value}</p>
                                        </div>
                                        <div className={`w-14 h-14 rounded-xl bg-gradient-to-br ${stat.color} flex items-center justify-center shadow-lg group-hover:scale-110 transition-transform`}>
                                            <Icon className="h-7 w-7 text-white" />
                                        </div>
                                    </div>
                                </Card>
                            </Link>
                        );
                    })}
                </div>

                {/* Quick Actions */}
                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    {/* Recent Attendance */}
                    <Card>
                        <div className="flex items-center justify-between mb-4">
                            <h2 className="text-xl font-bold text-white">Recent Attendance</h2>
                            <Link to="/attendance" className="text-primary-400 hover:text-primary-300 flex items-center gap-1 text-sm">
                                View All <ArrowRight className="h-4 w-4" />
                            </Link>
                        </div>
                        {recentAttendance.length > 0 ? (
                            <div className="space-y-3">
                                {recentAttendance.map((attendance) => (
                                    <div key={attendance.attendanceId} className="flex items-center justify-between p-3 rounded-lg bg-white/5 hover:bg-white/10 transition-colors">
                                        <div>
                                            <p className="font-medium text-white">{attendance.employeeName}</p>
                                            <p className="text-sm text-gray-400">{formatDate(attendance.attendanceDate)}</p>
                                        </div>
                                        <div className="text-right">
                                            <p className="text-sm text-gray-300">
                                                {attendance.checkInTime && formatTime(attendance.checkInTime)}
                                                {attendance.checkOutTime && ` - ${formatTime(attendance.checkOutTime)}`}
                                            </p>
                                            <span className={`inline-block px-2 py-1 rounded text-xs ${attendance.attendanceStatus === 'CHECK_IN' ? 'bg-emerald-500/20 text-emerald-400' : 'bg-blue-500/20 text-blue-400'
                                                }`}>
                                                {attendance.attendanceStatus}
                                            </span>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <p className="text-gray-400 text-center py-8">No recent attendance records</p>
                        )}
                    </Card>

                    {/* Quick Actions */}
                    <Card>
                        <h2 className="text-xl font-bold text-white mb-4">Quick Actions</h2>
                        <div className="space-y-3">
                            <Link to="/employees/new" className="block p-4 rounded-lg bg-gradient-to-r from-primary-600/20 to-primary-700/20 border border-primary-500/30 hover:from-primary-600/30 hover:to-primary-700/30 transition-all group">
                                <div className="flex items-center gap-3">
                                    <div className="w-10 h-10 rounded-lg bg-primary-600 flex items-center justify-center">
                                        <Users className="h-5 w-5 text-white" />
                                    </div>
                                    <div>
                                        <p className="font-medium text-white group-hover:text-primary-300 transition-colors">Add New Employee</p>
                                        <p className="text-sm text-gray-400">Create a new employee profile</p>
                                    </div>
                                </div>
                            </Link>

                            <Link to="/attendance" className="block p-4 rounded-lg bg-gradient-to-r from-emerald-600/20 to-teal-700/20 border border-emerald-500/30 hover:from-emerald-600/30 hover:to-teal-700/30 transition-all group">
                                <div className="flex items-center gap-3">
                                    <div className="w-10 h-10 rounded-lg bg-emerald-600 flex items-center justify-center">
                                        <Clock className="h-5 w-5 text-white" />
                                    </div>
                                    <div>
                                        <p className="font-medium text-white group-hover:text-emerald-300 transition-colors">Track Attendance</p>
                                        <p className="text-sm text-gray-400">Scan RFID or view history</p>
                                    </div>
                                </div>
                            </Link>

                            <Link to="/payroll" className="block p-4 rounded-lg bg-gradient-to-r from-amber-600/20 to-orange-700/20 border border-amber-500/30 hover:from-amber-600/30 hover:to-orange-700/30 transition-all group">
                                <div className="flex items-center gap-3">
                                    <div className="w-10 h-10 rounded-lg bg-amber-600 flex items-center justify-center">
                                        <DollarSign className="h-5 w-5 text-white" />
                                    </div>
                                    <div>
                                        <p className="font-medium text-white group-hover:text-amber-300 transition-colors">Generate Payroll</p>
                                        <p className="text-sm text-gray-400">Process monthly payments</p>
                                    </div>
                                </div>
                            </Link>
                        </div>
                    </Card>
                </div>
            </div>
        </Layout>
    );
}

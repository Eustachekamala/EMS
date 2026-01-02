import { useState, useEffect } from 'react';
import toast from 'react-hot-toast';
import { DollarSign, Calendar } from 'lucide-react';
import Layout from '@/components/layout/Layout';
import Card from '@/components/common/Card';
import Button from '@/components/common/Button';
import Input from '@/components/common/Input';
import Modal from '@/components/common/Modal';
import LoadingSpinner from '@/components/common/LoadingSpinner';
import { payrollService } from '@/services/payrollService';
import { employeeService } from '@/services/employeeService';
import type { EmployeeResponse, PayrollResponse, PayrollHistory } from '@/types';
import { formatCurrency, formatDate } from '@/utils/formatters';

function GlobalHistoryTable() {
    const [history, setHistory] = useState<PayrollHistory[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchHistory = async () => {
            try {
                const data = await payrollService.getAllPayrolls();
                setHistory(data);
            } catch (error) {
                console.error('Failed to fetch history', error);
            } finally {
                setLoading(false);
            }
        };
        fetchHistory();
    }, []);

    if (loading) return <tr><td colSpan={6} className="text-center py-8"><LoadingSpinner size="sm" /></td></tr>;
    if (history.length === 0) return <tr><td colSpan={6} className="text-center py-8 text-gray-500">No payment history found.</td></tr>;

    return (
        <>
            {history.map((record) => (
                <tr key={record.payrollId} className="border-b border-white/5 hover:bg-white/5 transition-colors">
                    <td className="py-3 px-4 font-medium">{record.employeeName}</td>
                    <td className="py-3 px-4 text-gray-400">{record.jobTitle}</td>
                    <td className="py-3 px-4 text-gray-400">{record.departmentName}</td>
                    <td className="py-3 px-4 text-gray-400">
                        {new Date(record.year, record.month - 1).toLocaleDateString('en-US', { month: 'short', year: 'numeric' })}
                    </td>
                    <td className="py-3 px-4 text-gray-400">{formatDate(record.paymentDate)}</td>
                    <td className="py-3 px-4 text-right font-bold text-emerald-400">{formatCurrency(record.salary)}</td>
                </tr>
            ))}
        </>
    );
}

export default function Payroll() {
    const [employees, setEmployees] = useState<EmployeeResponse[]>([]);
    const [selectedEmployeeId, setSelectedEmployeeId] = useState<number | null>(null);
    const [payrolls, setPayrolls] = useState<PayrollResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [generateModalOpen, setGenerateModalOpen] = useState(false);
    const [generating, setGenerating] = useState(false);
    const [year, setYear] = useState(new Date().getFullYear());
    const [month, setMonth] = useState(new Date().getMonth() + 1);

    useEffect(() => {
        fetchEmployees();
    }, []);

    const fetchEmployees = async () => {
        try {
            const data = await employeeService.getAllEmployees();
            setEmployees(data);
        } catch (error) {
            console.error('Failed to fetch employees:', error);
        } finally {
            setLoading(false);
        }
    };

    const fetchPayrollsForEmployee = async (employeeId: number) => {
        try {
            const data = await payrollService.getEmployeePayrolls(employeeId);
            setPayrolls(data);
            setSelectedEmployeeId(employeeId);
        } catch (error) {
            console.error('Failed to fetch payrolls:', error);
        }
    };

    const handleGeneratePayroll = async () => {
        if (!selectedEmployeeId) return;
        setGenerating(true);
        const toastId = toast.loading('Generating payroll...');
        try {
            await payrollService.generatePayroll(selectedEmployeeId, year, month);
            await fetchPayrollsForEmployee(selectedEmployeeId);
            setGenerateModalOpen(false);
            toast.success('Payroll generated successfully!', { id: toastId });
        } catch (error) {
            toast.error('Failed to generate payroll. It may already exist for this period.', { id: toastId });
        } finally {
            setGenerating(false);
        }
    };

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
                        <h1 className="text-3xl font-bold gradient-text">Payroll Management</h1>
                        <p className="text-gray-400 mt-1">Generate and manage employee payrolls</p>
                    </div>
                </div>

                {/* Tabs */}
                <div className="flex space-x-4 border-b border-white/10 pb-2 mb-6">
                    <button
                        className={`pb-2 px-4 text-sm font-medium transition-colors relative ${!selectedEmployeeId && !loading && employees.length > 0 ? 'text-primary-400' : 'text-gray-400 hover:text-white'}`}
                        onClick={() => setSelectedEmployeeId(null)}
                    >
                        Global History
                        {(!selectedEmployeeId) && <div className="absolute bottom-0 left-0 w-full h-0.5 bg-primary-500 rounded-t-full" />}
                    </button>
                    <button
                        className={`pb-2 px-4 text-sm font-medium transition-colors relative ${selectedEmployeeId ? 'text-primary-400' : 'text-gray-400 hover:text-white'}`}
                        onClick={() => setSelectedEmployeeId(employees[0]?.id || null)}
                    >
                        Manage by Employee
                        {selectedEmployeeId && <div className="absolute bottom-0 left-0 w-full h-0.5 bg-primary-500 rounded-t-full" />}
                    </button>
                </div>

                {!selectedEmployeeId ? (
                    /* Global History View */
                    <Card>
                        <h2 className="text-xl font-bold text-white mb-6">All Payment History</h2>
                        <div className="overflow-x-auto">
                            <table className="w-full text-left border-collapse">
                                <thead>
                                    <tr className="border-b border-white/10 text-gray-400 text-sm">
                                        <th className="py-3 px-4">Employee</th>
                                        <th className="py-3 px-4">Position</th>
                                        <th className="py-3 px-4">Department</th>
                                        <th className="py-3 px-4">Period</th>
                                        <th className="py-3 px-4">Payment Date</th>
                                        <th className="py-3 px-4 text-right">Amount</th>
                                    </tr>
                                </thead>
                                <tbody className="text-gray-300">
                                    <GlobalHistoryTable />
                                </tbody>
                            </table>
                        </div>
                    </Card>
                ) : (
                    /* Existing Employee Management View */
                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                        {/* Employee List */}
                        <Card className="lg:col-span-1">
                            <h2 className="text-xl font-bold text-white mb-4">Select Employee</h2>
                            <div className="space-y-2 max-h-[600px] overflow-y-auto">
                                {employees.map((employee) => (
                                    <button
                                        key={employee.id}
                                        onClick={() => fetchPayrollsForEmployee(employee.id)}
                                        className={`w-full text-left p-3 rounded-lg transition-all ${selectedEmployeeId === employee.id
                                            ? 'bg-primary-600/20 border border-primary-500/30'
                                            : 'bg-white/5 hover:bg-white/10'
                                            }`}
                                    >
                                        <p className="font-medium text-white">{`${employee.firstname} ${employee.lastname}`}</p>
                                        <p className="text-sm text-gray-400">{employee.jobTitle || 'No Position'}</p>
                                    </button>
                                ))}
                            </div>
                        </Card>

                        {/* Payroll History */}
                        <Card className="lg:col-span-2">
                            <div className="flex items-center justify-between mb-4">
                                <h2 className="text-xl font-bold text-white">Employee History</h2>
                                <Button onClick={() => setGenerateModalOpen(true)} size="sm">
                                    <DollarSign className="h-4 w-4" />
                                    Generate Payroll
                                </Button>
                            </div>

                            {payrolls.length > 0 ? (
                                <div className="space-y-3">
                                    {payrolls.map((payroll) => (
                                        <div
                                            key={payroll.payrollId}
                                            className="p-4 rounded-lg bg-white/5 hover:bg-white/10 transition-colors border border-white/10"
                                        >
                                            <div className="flex items-center justify-between mb-2">
                                                <div className="flex items-center gap-2">
                                                    <Calendar className="h-5 w-5 text-gray-400" />
                                                    <span className="font-medium text-white">
                                                        {new Date(payroll.payrollYear, payroll.payrollMonth - 1).toLocaleDateString('en-US', {
                                                            month: 'long',
                                                            year: 'numeric',
                                                        })}
                                                    </span>
                                                </div>
                                                <span className="text-2xl font-bold text-emerald-400">
                                                    {formatCurrency(payroll.salary)}
                                                </span>
                                            </div>
                                            <p className="text-sm text-gray-400">
                                                Payment Date: {formatDate(payroll.paymentDate)}
                                            </p>
                                        </div>
                                    ))}
                                </div>
                            ) : (
                                <p className="text-gray-400 text-center py-12">No payroll records found for this employee</p>
                            )}
                        </Card>
                    </div>
                )}
            </div>

            {/* Generate Payroll Modal */}
            <Modal
                isOpen={generateModalOpen}
                onClose={() => setGenerateModalOpen(false)}
                title="Generate Payroll"
                size="sm"
            >
                <div className="space-y-4">
                    <p className="text-gray-300">
                        Generate payroll for the selected employee for a specific month and year.
                    </p>
                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="Year"
                            type="number"
                            value={year}
                            onChange={(e) => setYear(parseInt(e.target.value))}
                            min="2020"
                            max="2100"
                        />
                        <Input
                            label="Month"
                            type="number"
                            value={month}
                            onChange={(e) => setMonth(parseInt(e.target.value))}
                            min="1"
                            max="12"
                        />
                    </div>
                    <div className="flex gap-3 justify-end">
                        <Button variant="secondary" onClick={() => setGenerateModalOpen(false)}>
                            Cancel
                        </Button>
                        <Button onClick={handleGeneratePayroll} isLoading={generating}>
                            Generate
                        </Button>
                    </div>
                </div>
            </Modal>
        </Layout>
    );
}

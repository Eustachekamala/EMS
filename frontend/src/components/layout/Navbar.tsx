import { Link, useLocation } from 'react-router-dom';
import { Users, Clock, DollarSign, Briefcase, LayoutDashboard, Menu } from 'lucide-react';
import { useState } from 'react';
import { cn } from '@/utils/cn';
import { useUIStore } from '@/store/uiStore';

const navLinks = [
    { to: '/dashboard', label: 'Dashboard', icon: LayoutDashboard },
    { to: '/employees', label: 'Employees', icon: Users },
    { to: '/attendance', label: 'Attendance', icon: Clock },
    { to: '/payroll', label: 'Payroll', icon: DollarSign },
    { to: '/jobs', label: 'Jobs', icon: Briefcase },
];

export default function Navbar() {
    const location = useLocation();
    const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
    const { enterpriseName } = useUIStore();

    return (
        <nav className="glass-dark border-b border-white/10 sticky top-0 z-40">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex items-center justify-between h-16">
                    {/* Logo */}
                    <Link to="/" className="flex items-center space-x-2">
                        <span className="text-xl font-bold gradient-text hidden sm:block">
                            {enterpriseName || 'EMS Portal'}
                        </span>
                    </Link>

                    {/* Desktop Navigation */}
                    <div className="hidden md:flex items-center space-x-1">
                        {navLinks.map((link) => {
                            const Icon = link.icon;
                            const isActive = location.pathname === link.to;
                            return (
                                <Link
                                    key={link.to}
                                    to={link.to}
                                    className={cn(
                                        'flex items-center gap-2 px-4 py-2 rounded-lg transition-all duration-200',
                                        isActive
                                            ? 'bg-primary-600/20 text-primary-400 shadow-lg shadow-primary-500/20'
                                            : 'text-gray-300 hover:bg-white/10 hover:text-white'
                                    )}
                                >
                                    <Icon className="h-5 w-5" />
                                    <span className="font-medium">{link.label}</span>
                                </Link>
                            );
                        })}
                    </div>

                    {/* Mobile menu button */}
                    <button
                        onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
                        className="md:hidden p-2 rounded-lg hover:bg-white/10 transition-colors"
                    >
                        <Menu className="h-6 w-6 text-gray-300" />
                    </button>
                </div>

                {/* Mobile Navigation */}
                {isMobileMenuOpen && (
                    <div className="md:hidden py-4 space-y-2 animate-fade-in">
                        {navLinks.map((link) => {
                            const Icon = link.icon;
                            const isActive = location.pathname === link.to;
                            return (
                                <Link
                                    key={link.to}
                                    to={link.to}
                                    onClick={() => setIsMobileMenuOpen(false)}
                                    className={cn(
                                        'flex items-center gap-3 px-4 py-3 rounded-lg transition-all duration-200',
                                        isActive
                                            ? 'bg-primary-600/20 text-primary-400'
                                            : 'text-gray-300 hover:bg-white/10 hover:text-white'
                                    )}
                                >
                                    <Icon className="h-5 w-5" />
                                    <span className="font-medium">{link.label}</span>
                                </Link>
                            );
                        })}
                    </div>
                )}
            </div>
        </nav>
    );
}

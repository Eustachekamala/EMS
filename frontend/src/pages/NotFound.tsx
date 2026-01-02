import { Link } from 'react-router-dom';
import { Home, ArrowLeft } from 'lucide-react';
import Layout from '@/components/layout/Layout';
import Card from '@/components/common/Card';
import Button from '@/components/common/Button';

export default function NotFound() {
    return (
        <Layout>
            <div className="flex items-center justify-center min-h-[60vh]">
                <Card className="max-w-md text-center">
                    <div className="mb-6">
                        <h1 className="text-8xl font-bold gradient-text mb-4">404</h1>
                        <h2 className="text-2xl font-semibold text-white mb-2">Page Not Found</h2>
                        <p className="text-gray-400">
                            The page you're looking for doesn't exist or has been moved.
                        </p>
                    </div>

                    <div className="flex gap-3 justify-center">
                        <Link to="/">
                            <Button>
                                <Home className="h-5 w-5" />
                                Go Home
                            </Button>
                        </Link>
                        <Button variant="secondary" onClick={() => window.history.back()}>
                            <ArrowLeft className="h-5 w-5" />
                            Go Back
                        </Button>
                    </div>
                </Card>
            </div>
        </Layout>
    );
}

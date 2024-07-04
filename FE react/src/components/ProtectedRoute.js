import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';

function ProtectedRoute({ children, requiredRole }) {
    const { auth } = useAuth();
    const [isAuthorized, setIsAuthorized] = useState(false);

    useEffect(() => {
        if (auth.isAuthenticated && (!requiredRole || auth.role === requiredRole)) {
            setIsAuthorized(true);
        } else {
            setIsAuthorized(false);
        }
    }, [auth, requiredRole]);

    if (!isAuthorized) {
        return <div>You are not authorized to view this page.</div>;
    }

    return children;
}

export default ProtectedRoute;

import ApiErrorBoundary from '@components/common/ApiErrorBoundary';
import { FallbackProps } from 'react-error-boundary';
import { Outlet } from 'react-router-dom';

function ApiFallback({ error }: FallbackProps) {
  return <div>{error.message}</div>;
}

export default function App() {
  return (
    <>
      {/* Header, SideBar를 여기에 추가하면 됩니다. */}
      <ApiErrorBoundary FallbackComponent={ApiFallback}>
        <Outlet />
      </ApiErrorBoundary>
    </>
  );
}

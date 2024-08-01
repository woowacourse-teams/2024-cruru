import ApiError from '@api/ApiError';
import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { ErrorBoundary, ErrorBoundaryPropsWithComponent, FallbackProps } from 'react-error-boundary';

interface ApiErrorBoundaryProps extends ErrorBoundaryPropsWithComponent {
  FallbackComponent: React.ComponentType<FallbackProps>;
}

export default function ApiErrorBoundary({ FallbackComponent, children }: ApiErrorBoundaryProps) {
  const fallbackRender = (props: FallbackProps) => {
    const error = props.error as ApiError;
    if (!(error instanceof ApiError)) {
      throw error;
    }

    return (
      <FallbackComponent
        {...props}
        error={error}
      />
    );
  };

  return (
    <QueryErrorResetBoundary>
      {({ reset }) => (
        <ErrorBoundary
          onReset={reset}
          onError={() => {
            console.log('error');
          }}
          fallbackRender={fallbackRender}
        >
          {children}
        </ErrorBoundary>
      )}
    </QueryErrorResetBoundary>
  );
}

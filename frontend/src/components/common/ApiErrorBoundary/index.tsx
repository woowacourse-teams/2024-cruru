import ApiError from '@api/ApiError';
import { QueryErrorResetBoundary } from '@tanstack/react-query';
import { ErrorBoundary, ErrorBoundaryPropsWithComponent, FallbackProps } from 'react-error-boundary';
import * as Sentry from '@sentry/react';

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
          onError={(error, info) => {
            const apiError = error as ApiError;

            Sentry.withScope((scope) => {
              scope.setLevel('error');
              scope.setTag('statusCode', apiError.statusCode);
              scope.setTag('method', apiError.method);
              scope.setExtra('componentStack', info.componentStack);
              scope.captureException(apiError);
            });
          }}
          fallbackRender={fallbackRender}
        >
          {children}
        </ErrorBoundary>
      )}
    </QueryErrorResetBoundary>
  );
}

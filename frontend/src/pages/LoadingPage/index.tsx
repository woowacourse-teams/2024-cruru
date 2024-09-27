import { PageLoadingSpinner } from '@components/_common/atoms/Spinner';
import { useEffect, useState } from 'react';
import S from './style';

export default function LoadingPage() {
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setIsVisible(true);
    }, 1000);

    return () => clearTimeout(timer);
  }, []);

  return <S.PageLayout>{isVisible && <PageLoadingSpinner />}</S.PageLayout>;
}

import { useLayoutEffect, useRef, useState } from 'react';
import S from './style';

interface ProcessDescriptionProps {
  description: string;
}

export default function ProcessDescription({ description }: ProcessDescriptionProps) {
  const [isOverflow, setOverflow] = useState(false);
  const [showMore, setShowMore] = useState(false);

  const toggleShowMore = () => setShowMore((prev) => !prev);

  const descriptionRef = useRef<HTMLDivElement>(null);
  useLayoutEffect(() => {
    if (descriptionRef.current) {
      setOverflow(descriptionRef.current.scrollWidth > descriptionRef.current.clientWidth);
    }
  }, [description]);

  return (
    <S.Container showMore={showMore}>
      <S.Description
        ref={descriptionRef}
        showMore={showMore}
      >
        {description}
      </S.Description>
      {isOverflow && <S.MoreButton onClick={toggleShowMore}>{showMore ? '접기' : '..더보기'}</S.MoreButton>}
    </S.Container>
  );
}

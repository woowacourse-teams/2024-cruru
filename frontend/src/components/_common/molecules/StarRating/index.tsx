import StarMarker from '@components/_common/atoms/StarMarker';
import { useState } from 'react';

const MAX_RATING = 5;

interface StarRatingProps {
  rating: number;
  handleRating: (rating: number) => void;
}

export default function StarRating({ rating, handleRating }: StarRatingProps) {
  const [hoverRating, setHoverRating] = useState<number>(0);

  const handleMouseEnter = (currentRating: number) => {
    setHoverRating(currentRating);
  };

  const handleMouseLeave = () => {
    setHoverRating(0);
  };

  const handleRatingMemoized = (currentRating: number) => {
    handleRating(currentRating);
  };

  return (
    <>
      {Array.from({ length: MAX_RATING }).map((_, index) => {
        const currentRating = index + 1;
        const isSelected = currentRating <= (hoverRating || rating);

        return (
          <StarMarker
            key={`score-${index + 1}`}
            isSelected={isSelected}
            handleMouseLeave={handleMouseLeave}
            handleMouseEnter={() => handleMouseEnter(currentRating)}
            handleClick={() => handleRatingMemoized(currentRating)}
          />
        );
      })}
    </>
  );
}

import React from 'react';
import { HiOutlineStar, HiStar } from 'react-icons/hi2';
import S from './style';

interface StarMarkerProps {
  isSelected: boolean;
  handleMouseEnter: () => void;
  handleMouseLeave: () => void;
  handleClick: () => void;
}

const shouldRerender = (prevProps: StarMarkerProps, nextProps: StarMarkerProps) =>
  prevProps.isSelected === nextProps.isSelected;

function StarMarker({ isSelected, handleMouseEnter, handleMouseLeave, handleClick }: StarMarkerProps) {
  return (
    <S.StarButton
      type="button"
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
      onClick={handleClick}
    >
      {isSelected ? <HiStar /> : <HiOutlineStar />}
    </S.StarButton>
  );
}

export default React.memo(StarMarker, shouldRerender);

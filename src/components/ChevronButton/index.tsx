import chevronLeft from '@assets/images/chevronLeft.svg';
import chevronRight from '@assets/images/chevronRight.svg';
import chevronUp from '@assets/images/chevronUp.svg';
import chevronDown from '@assets/images/chevronDown.svg';
import S from './style';

interface ChevronButtonProps {
  direction: 'left' | 'right' | 'up' | 'down';
  size: 'sm' | 'md' | 'lg';
}

const iconMap = {
  left: chevronLeft,
  right: chevronRight,
  up: chevronUp,
  down: chevronDown,
};

export default function ChevronButton({ direction, size }: ChevronButtonProps) {
  return (
    <S.Button size={size}>
      <img
        src={iconMap[direction]}
        alt={`chevron-${direction}`}
      />
    </S.Button>
  );
}

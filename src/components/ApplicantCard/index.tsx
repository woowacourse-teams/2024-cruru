import { useState, useRef, useEffect } from 'react';

import EllipsisIcon from '@assets/icons/ellipsis.svg';
import S from './style';
import formatDate from '../../utils/formatDate';
import IconButton from '../IconButton';
import PopOverMenu from '../PopOverMenu';

import { PopOverMenuItem } from '@/types/common';

interface ApplicantCardProps {
  name: string;
  createdAt: string;
  popOverMenuItems: PopOverMenuItem[];
}

export default function ApplicantCard({ name, createdAt, popOverMenuItems }: ApplicantCardProps) {
  const [isPopOverOpen, setIsPopOverOpen] = useState<boolean>(false);
  const optionButtonWrapperRef = useRef<HTMLDivElement>(null);

  const handleClickPopOverButton = (event: React.MouseEvent) => {
    event.stopPropagation();
    setIsPopOverOpen((prevState) => !prevState);
  };

  const handleClickOutside = (event: MouseEvent) => {
    if (optionButtonWrapperRef.current && !optionButtonWrapperRef.current.contains(event.target as Node)) {
      setIsPopOverOpen(false);
    }
  };

  useEffect(() => {
    if (isPopOverOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isPopOverOpen]);

  return (
    <S.CardContainer>
      <S.CardDetail>
        <S.CardHeader>{name}</S.CardHeader>
        <S.CardDate>{`지원 일자: ${formatDate(createdAt)}`}</S.CardDate>
      </S.CardDetail>
      <S.OptionButtonWrapper>
        <div ref={optionButtonWrapperRef}>
          <IconButton
            type="button"
            outline={false}
            onClick={handleClickPopOverButton}
          >
            <img
              alt="테스트"
              src={EllipsisIcon}
            />
          </IconButton>
          <PopOverMenu
            isOpen={isPopOverOpen}
            items={popOverMenuItems}
            popOverPosition="3.5rem 0 0 -11.5rem"
          />
        </div>
      </S.OptionButtonWrapper>
    </S.CardContainer>
  );
}

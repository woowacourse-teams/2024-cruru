import { HiOutlineClock, HiOutlineChat } from 'react-icons/hi';
import { HiEllipsisVertical } from 'react-icons/hi2';

import { useRef, useEffect, useCallback } from 'react';

import IconButton from '@components/_common/atoms/IconButton';
import PopOverMenu from '@components/_common/molecules/PopOverMenu';
import formatDate from '@utils/formatDate';

import { useDropdown } from '@contexts/DropdownContext';

import type { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';
import DropdownItemRenderer from '@components/_common/molecules/DropdownItemRenderer';
import S from './style';

interface ApplicantCardProps {
  name: string;
  isRejected: boolean;
  createdAt: string;
  evaluationCount: number;
  averageScore: number;
  popOverMenuItems: DropdownItemType[];
  onCardClick: () => void;
}

export default function ApplicantCard({
  name,
  isRejected,
  createdAt,
  evaluationCount,
  averageScore,
  popOverMenuItems,
  onCardClick,
}: ApplicantCardProps) {
  const { isOpen, open, close } = useDropdown();
  const optionButtonWrapperRef = useRef<HTMLDivElement>(null);

  const evaluationString = averageScore ? `★ ${averageScore.toFixed(1)}` : '평가 대기 중';

  const handleClickPopOverButton = (event: React.MouseEvent) => {
    event.stopPropagation();
    if (isOpen) close();
    if (!isOpen) open();
  };

  const handleClickOutside = useCallback(
    (event: MouseEvent) => {
      if (optionButtonWrapperRef.current && !optionButtonWrapperRef.current.contains(event.target as Node)) {
        close();
      }
    },
    [close],
  );

  const handleMouseLeave = () => {
    close();
  };

  const cardClickHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    onCardClick();
  };

  useEffect(() => {
    if (isOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isOpen, handleClickOutside]);

  return (
    <S.CardContainer
      onMouseLeave={handleMouseLeave}
      onClick={cardClickHandler}
    >
      <S.CardDetail>
        <S.CardHeader>{name}</S.CardHeader>
        <S.CardEvaluationFlag
          averageScore={averageScore}
          evaluationCount={evaluationCount}
        >
          {evaluationString}
        </S.CardEvaluationFlag>
        <S.CardInfoContainer>
          <S.CardInfo>
            <HiOutlineClock size="1.2rem" />
            {formatDate(createdAt)}
          </S.CardInfo>

          <S.CardInfo>
            <HiOutlineChat size="1.2rem" />
            {evaluationCount}
          </S.CardInfo>
        </S.CardInfoContainer>
      </S.CardDetail>

      <S.OptionButtonWrapper>
        <div ref={optionButtonWrapperRef}>
          <IconButton
            type="button"
            outline={false}
            onClick={handleClickPopOverButton}
            disabled={isRejected}
          >
            <HiEllipsisVertical />
          </IconButton>
          <PopOverMenu
            isOpen={isOpen}
            popOverPosition="3.5rem 0 0 -6rem"
          >
            <DropdownItemRenderer
              items={popOverMenuItems}
              subContentPlacement="left"
            />
          </PopOverMenu>
        </div>
      </S.OptionButtonWrapper>
    </S.CardContainer>
  );
}

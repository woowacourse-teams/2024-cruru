import { HiOutlineClock, HiOutlineChat } from 'react-icons/hi';
import { HiEllipsisVertical } from 'react-icons/hi2';

import React, { useRef, useState } from 'react';

import formatDate from '@utils/formatDate';

import type { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';
import IconButton from '@components/_common/atoms/IconButton';
import Popover from '@components/_common/atoms/Popover';
import DropdownItemRenderer from '@components/_common/molecules/DropdownItemRenderer';
import CheckBox from '@components/_common/atoms/CheckBox';

import S from './style';

interface ApplicantCardProps {
  name: string;
  isRejected: boolean;
  createdAt: string;
  evaluationCount: number;
  averageScore: number;
  popOverMenuItems: DropdownItemType[];
  isSelectMode: boolean;
  isSelected: boolean;
  onCardClick: () => void;
  onSelectApplicant: (isChecked: boolean) => void;
}

export default function ApplicantCard({
  name,
  isRejected,
  createdAt,
  evaluationCount,
  averageScore,
  popOverMenuItems,
  isSelectMode,
  isSelected,
  onCardClick,
  onSelectApplicant,
}: ApplicantCardProps) {
  const optionButtonWrapperRef = useRef<HTMLButtonElement>(null);
  const [isPopoverOpen, setIsPopoverOpen] = useState(false);
  const [isCardHover, setIsCardHover] = useState(false);

  const evaluationString = evaluationCount > 0 ? averageScore.toFixed(1) : '―';

  const handleClickPopOverButton = (event: React.MouseEvent) => {
    event.stopPropagation();
    if (isPopoverOpen) setIsPopoverOpen(false);
    if (!isPopoverOpen) setIsPopoverOpen(true);
  };

  const handleMouseEnter = () => {
    setIsCardHover(true);
  };

  const handleMouseLeave = () => {
    setIsCardHover(false);
    setIsPopoverOpen(false);
  };

  const cardClickHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    if (isSelectMode) {
      onSelectApplicant(!isSelected);
      return;
    }
    onCardClick();
  };

  return (
    <S.CardContainer
      isHover={isCardHover}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
      onClick={cardClickHandler}
    >
      <S.CardDetail>
        <S.CardHeader>{name}</S.CardHeader>
        <S.CardInfoContainer>
          <S.CardEvaluationFlag
            averageScore={averageScore}
            isScoreExists={evaluationCount > 0}
          >
            ★
            <S.CardEvaluationFlagScore isScoreExists={evaluationCount > 0}>
              {evaluationString}
            </S.CardEvaluationFlagScore>
          </S.CardEvaluationFlag>
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
        {isSelectMode && (
          <CheckBox
            isChecked={isSelected}
            onToggle={() => {}}
          />
        )}
        {!isSelectMode && (
          <>
            <IconButton
              ref={optionButtonWrapperRef}
              type="button"
              outline={false}
              onClick={handleClickPopOverButton}
              disabled={isRejected}
            >
              <HiEllipsisVertical />
            </IconButton>
            <Popover
              isOpen={isPopoverOpen}
              anchorEl={optionButtonWrapperRef.current}
              onClose={() => setIsPopoverOpen(false)}
            >
              <S.PopoverWrapper>
                <DropdownItemRenderer items={popOverMenuItems} />
              </S.PopoverWrapper>
            </Popover>
          </>
        )}
      </S.OptionButtonWrapper>
    </S.CardContainer>
  );
}

import { useEffect, useState } from 'react';

import { useMultiApplicant } from '@contexts/MultiApplicantContext';
import { useFloatingEmailForm } from '@contexts/FloatingEmailFormContext';
import { DropdownProvider } from '@contexts/DropdownContext';
import type { SimpleProcess } from '@hooks/useProcess';

import useApplicant from '@hooks/useApplicant';
import ToggleSwitch from '@components/_common/atoms/ToggleSwitch';
import Dropdown from '@components/_common/molecules/Dropdown';
import DropdownItemRenderer, { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';

import S from './style';

interface MultiSelectToggleProps {
  isToggled: boolean;
  processes: SimpleProcess[];
  selectedApplicantIds: number[];
}

export default function MultiSelectToggle({ isToggled, processes, selectedApplicantIds }: MultiSelectToggleProps) {
  const { toggleIsMultiType, resetApplicants } = useMultiApplicant();
  const { mutate: moveApplicantProcess } = useApplicant({});
  const { open: sideEmailFormOpen } = useFloatingEmailForm();

  const [isDropdownVisible, setIsDropdownVisible] = useState<boolean>(false);
  const [isToggleVisible, setIsToggleVisible] = useState<boolean>(true);

  useEffect(() => {
    if (isToggled) {
      setIsToggleVisible(false);
      const timer = setTimeout(() => setIsDropdownVisible(true), 200);
      return () => clearTimeout(timer);
    }

    setIsDropdownVisible(false);
    const timer = setTimeout(() => setIsToggleVisible(true), 300);
    return () => clearTimeout(timer);
  }, [isToggled]);

  const handleToggleMultiType = () => {
    if (isToggled) resetApplicants();
    toggleIsMultiType();
  };

  const menuItems: DropdownItemType[] = [
    {
      type: 'subTrigger',
      id: 'moveProcess',
      name: '단계 이동',
      items: processes.map(({ processName, processId }) => ({
        type: 'clickable',
        id: processId,
        name: processName,
        onClick: ({ targetProcessId }) => {
          moveApplicantProcess({ processId: targetProcessId, applicants: selectedApplicantIds });
        },
      })),
    },
    {
      type: 'clickable',
      id: 'emailButton',
      name: '이메일 보내기',
      hasSeparate: true,
      onClick: () => {
        sideEmailFormOpen();
      },
    },
    {
      type: 'clickable',
      id: 'rejectButton',
      name: '불합격 처리',
      isHighlight: true,
      hasSeparate: true,
      onClick: () => {
        // TODO: 일괄 불합격/불합격 해제에 해당하는 API 연결 Mutation 구현이 필요합니다. (24/10/16 아르)
        console.log(`${selectedApplicantIds.join(', ')} 지원자들을 모두 불합격 처리합니다.`);
      },
    },
  ];

  return (
    <S.Wrapper>
      <S.ToggleWrapper isVisible={isToggleVisible}>
        <S.ToggleLabel>여러 지원자 선택</S.ToggleLabel>
        <ToggleSwitch
          width="4rem"
          onChange={handleToggleMultiType}
          isChecked={isToggled}
          isDisabled={false}
        />
      </S.ToggleWrapper>

      <S.DropdownWrapper
        isVisible={isDropdownVisible}
        isDisabled={selectedApplicantIds.length === 0}
      >
        <DropdownProvider>
          <Dropdown
            initValue="실행할 작업"
            width={120}
            isShadow={false}
            disabled={selectedApplicantIds.length === 0}
          >
            <DropdownItemRenderer items={menuItems} />
          </Dropdown>
        </DropdownProvider>
      </S.DropdownWrapper>
    </S.Wrapper>
  );
}

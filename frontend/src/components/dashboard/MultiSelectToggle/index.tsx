import { useEffect, useState } from 'react';

import { useMultiApplicant } from '@contexts/MultiApplicantContext';
import { useFloatingEmailForm } from '@contexts/FloatingEmailFormContext';
import { DropdownProvider } from '@contexts/DropdownContext';
import type { SimpleProcess } from '@hooks/useProcess';

import useApplicant from '@hooks/useApplicant';
import applicantsReject from '@hooks/useApplicantsReject';
import ToggleSwitch from '@components/_common/atoms/ToggleSwitch';
import Dropdown from '@components/_common/molecules/Dropdown';
import DropdownItemRenderer, { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';

import S from './style';

interface MultiSelectToggleProps {
  dashboardId: string;
  applyFormId: string;
  isToggled: boolean;
  processes: SimpleProcess[];
  selectedApplicantIds: number[];
  isRejectedApplicantsTab: boolean;
}

export default function MultiSelectToggle({
  dashboardId,
  applyFormId,
  isToggled,
  processes,
  selectedApplicantIds,
  isRejectedApplicantsTab,
}: MultiSelectToggleProps) {
  const { toggleIsMultiType, resetApplicants } = useMultiApplicant();
  const { mutate: moveApplicantProcess } = useApplicant({});
  const { open: sideEmailFormOpen } = useFloatingEmailForm();
  const rejectApplicants = applicantsReject.useRejectApplicants({ dashboardId, applyFormId });
  const unrejectApplicants = applicantsReject.useUnrejectApplicants({ dashboardId, applyFormId });

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
        if (isRejectedApplicantsTab) {
          unrejectApplicants.mutate({ applicantIds: selectedApplicantIds });
          return;
        }
        rejectApplicants.mutate({ applicantIds: selectedApplicantIds });
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

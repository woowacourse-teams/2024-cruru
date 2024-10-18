import { useParams } from 'react-router-dom';
import { useEffect } from 'react';

import useProcess, { SimpleProcess } from '@hooks/useProcess';

import InputField from '@components/_common/molecules/InputField';
import { useSearchApplicant } from '../useSearchApplicant';
import ApplicantSortDropdown from '../ApplicantSortDropdown';

import S from './style';

interface DashboardFunctionTabProps {
  processList: SimpleProcess[];
  onSearchName: (name: string) => void;
}

export default function DashboardFunctionTab({ processList, onSearchName }: DashboardFunctionTabProps) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };

  const { debouncedName, name, updateName } = useSearchApplicant();
  const {
    applicantSortDropdownProps: { sortOption, updateSortOption },
  } = useProcess({ dashboardId, applyFormId });

  useEffect(() => {
    onSearchName(debouncedName);
  }, [onSearchName, debouncedName]);

  console.log(processList);

  return (
    <S.Wrapper>
      <S.FunctionsContainer>
        <InputField
          type="search"
          placeholder="지원자 이름 검색"
          value={name}
          onChange={(e) => updateName(e.target.value)}
        />
        <ApplicantSortDropdown
          sortOption={sortOption}
          updateSortOption={updateSortOption}
        />
      </S.FunctionsContainer>
    </S.Wrapper>
  );
}

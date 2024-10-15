import { useSearchApplicant } from '@contexts/SearchApplicantContext';
import InputField from '../../_common/molecules/InputField';

export default function SearchApplicantInput() {
  const { name, handleName } = useSearchApplicant();

  return (
    <InputField
      type="search"
      placeholder="지원자 이름 검색"
      value={name}
      onChange={(e) => handleName(e.target.value)}
    />
  );
}

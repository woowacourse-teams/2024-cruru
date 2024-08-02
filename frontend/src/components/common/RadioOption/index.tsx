import S from './style';
import Radio from '../Radio';

interface RadioOptionProps {
  label: string;
  checked: boolean;
  onChange: () => void;
}

export default function RadioOption({ label, checked, onChange }: RadioOptionProps) {
  return (
    <S.Option>
      <Radio
        isChecked={checked}
        onToggle={onChange}
      />
      <S.Label>{label}</S.Label>
    </S.Option>
  );
}

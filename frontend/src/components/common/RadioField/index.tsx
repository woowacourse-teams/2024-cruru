import S from './style';
import RadioOption from '../RadioOption';

interface Option {
  label: string;
  value: string;
}

interface RadioFieldProps {
  options: Option[];
  selectedValue?: string;
  onChange: (value: string) => void;
}

export default function RadioField({ options, selectedValue, onChange }: RadioFieldProps) {
  return (
    <S.Container>
      {options.map((option) => (
        <RadioOption
          key={option.value}
          label={option.label}
          checked={selectedValue === option.value}
          onChange={() => onChange(option.value)}
        />
      ))}
    </S.Container>
  );
}
